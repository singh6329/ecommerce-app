package com.app.inventory_service.controllers;

import com.app.inventory_service.clients.OrdersFeignClient;
import com.app.inventory_service.dtos.InventoryDto;
import com.app.inventory_service.dtos.OrderRequestDto;
import com.app.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/callingOrders")
    public String callingOrders()
    {
        return ordersFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllInventories()
    {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getAllInventories(@PathVariable(name = "id") Long id)
    {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }
    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto)
    {
        Double price = inventoryService.reduceStock(orderRequestDto);
        return ResponseEntity.ok(price);
    }

    @PutMapping("/add-cancelled-stocks")
    public ResponseEntity<Void> addCancelledStocks(@RequestBody OrderRequestDto orderRequestDto)
    {
        inventoryService.addCancelledStocks(orderRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
