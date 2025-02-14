package com.app.order_service.services;

import com.app.order_service.clients.InventoryFeignClient;
import com.app.order_service.clients.ShippingFeignClient;
import com.app.order_service.dtos.OrderRequestDto;
import com.app.order_service.dtos.OrderRequestItemDto;
import com.app.order_service.dtos.ShippingDto;
import com.app.order_service.entities.OrderItem;
import com.app.order_service.entities.OrderStatus;
import com.app.order_service.entities.Orders;
import com.app.order_service.repositories.OrdersRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServices {
    private final OrdersRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;
    private final ShippingFeignClient shippingFeignClient;

    public List<OrderRequestDto> getAllOrders()
    {
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order,OrderRequestDto.class))
                .toList();
    }

    public OrderRequestDto getOrderById(Long id)
    {
        Orders order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Id doesn't exists!"));
        return modelMapper.map(order,OrderRequestDto.class);
    }

    @CircuitBreaker(name = "create-order",fallbackMethod = "createOrderFallback")
    @Transactional
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        Double totalPrice = inventoryFeignClient.reduceStocks(orderRequestDto);
        orderRequestDto.setTotalPrice(totalPrice);
        Orders orders = modelMapper.map(orderRequestDto, Orders.class);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        for(OrderItem orderItem: orders.getItems())
        {
            orderItem.setOrder(orders);
        }
        Orders savedOrders = orderRepository.save(orders);
        //inventoryFeignClient.getInventoryById(123L);
        shippingFeignClient.shipOrder(ShippingDto.builder().orderId(savedOrders.getId()).build());
        return modelMapper.map(savedOrders,OrderRequestDto.class);
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto,Throwable throwable) {
        log.info("Fallback method........");
        if(throwable instanceof FeignException feignException) {
            String error = feignException.contentUTF8();
            log.error(error);
        }
        log.error(throwable.getLocalizedMessage());
        return OrderRequestDto.builder().orderStatus(null).items(null).totalPrice(null).build();
    }

    public OrderRequestDto cancelOrder(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Provided id doesn't exists!"));
        if (orders.getOrderStatus().equals(OrderStatus.CANCELLED))
            throw new RuntimeException("Order is already cancelled!");
        orders.setOrderStatus(OrderStatus.CANCELLED);
        OrderRequestDto orderRequestDto = modelMapper.map(orders,OrderRequestDto.class);
        inventoryFeignClient.cancelOrders(orderRequestDto);
        shippingFeignClient.cancelShipping(ShippingDto.builder().orderId(orders.getId()).build());
        Orders updatedOrders= orderRepository.save(orders);
        return modelMapper.map(updatedOrders,OrderRequestDto.class);
    }
}
