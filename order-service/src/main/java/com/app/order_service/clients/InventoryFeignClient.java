package com.app.order_service.clients;

import com.app.order_service.dtos.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "inventory-service",path = "/inventory")
public interface InventoryFeignClient {

    @GetMapping("/{id}")
    void getInventoryById(@PathVariable(name = "id") Long id);

    @PutMapping("/reduce-stocks")
    Double reduceStocks(OrderRequestDto orderRequestDto);

    @PutMapping("/add-cancelled-stocks")
    void cancelOrders(OrderRequestDto orderRequestDto);

}
