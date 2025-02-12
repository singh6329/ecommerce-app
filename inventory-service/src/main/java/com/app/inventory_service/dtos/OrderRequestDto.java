package com.app.inventory_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Long id;

    private Double totalPrice;

    private List<OrderRequestItemDto> items;
}
