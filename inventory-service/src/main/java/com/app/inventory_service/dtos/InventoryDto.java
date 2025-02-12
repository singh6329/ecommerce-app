package com.app.inventory_service.dtos;

import lombok.Data;

@Data
public class InventoryDto {
    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

}
