package com.app.order_service.controllers;

import com.app.order_service.dtos.OrderRequestDto;
import com.app.order_service.services.OrderServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderServices orderServices;

    @GetMapping("/helloOrders")
    public String helloFromOrders(@RequestHeader("X-user-Id") Long userId)
    {
        //String userID = request.getHeader("X-user-Id");
        System.out.println(userId);
        return "Hello from Orders!..... You are calling from id: "+userId;
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders()
    {
        return ResponseEntity.ok(orderServices.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getAllOrdersById(@PathVariable(name = "id") Long id)
    {
        return ResponseEntity.ok(orderServices.getOrderById(id));
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto)
    {
        OrderRequestDto orderRequestDto1 = orderServices.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);
    }

    @PostMapping("/cancel-order/{id}")
    public ResponseEntity<OrderRequestDto> cancelOrder(@PathVariable Long id)
    {
        OrderRequestDto orderRequestDto1 = orderServices.cancelOrder(id);
        return ResponseEntity.ok(orderRequestDto1);
    }

}
