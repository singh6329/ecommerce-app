package com.app.inventory_service.dtos;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long id;

    private Integer productId;

    private Integer quantity;

}
