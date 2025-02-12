package com.app.shipping_service.controllers;

import com.app.shipping_service.dtos.ShippingDto;
import com.app.shipping_service.services.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/ship-order")
    public ResponseEntity<ShippingDto> shipOrder(@RequestBody ShippingDto shippingDto)
    {
        return ResponseEntity.ok(shippingService.shipOrder(shippingDto));
    }

    @PostMapping("/cancel-shipping")
    public ResponseEntity<Void> cancelShipping(@RequestBody ShippingDto shippingDto)
    {
        shippingService.cancelShipping(shippingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
