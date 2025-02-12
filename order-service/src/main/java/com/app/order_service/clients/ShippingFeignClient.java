package com.app.order_service.clients;

import com.app.order_service.dtos.ShippingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shipping-service",path = "/shipping")
public interface ShippingFeignClient {

    @PostMapping("/ship-order")
     void shipOrder(ShippingDto shippingDto);

    @PostMapping("/cancel-shipping")
    void cancelShipping(ShippingDto shippingDto);
}
