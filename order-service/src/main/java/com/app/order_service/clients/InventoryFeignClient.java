package com.app.order_service.clients;

import com.app.order_service.dtos.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "inventory-service",path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping("/reduce-stocks")
    Double reduceStocks(OrderRequestDto orderRequestDto);

    @PutMapping("/add-cancelled-stocks")
    void cancelOrders(OrderRequestDto orderRequestDto);

}
