package com.app.order_service.dtos;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingDto {
    private Long id;

    private Long orderId;

}
