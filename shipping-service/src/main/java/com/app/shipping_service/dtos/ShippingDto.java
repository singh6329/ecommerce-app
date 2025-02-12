package com.app.shipping_service.dtos;

import com.app.shipping_service.entities.enums.CourierService;
import com.app.shipping_service.entities.enums.ShippingStatus;
import lombok.Data;

@Data
public class ShippingDto {
    private Long id;

    private Long orderId;

    private ShippingStatus shippingStatus;

    private CourierService courierService;
}
