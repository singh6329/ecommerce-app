package com.app.order_service.dtos;

import com.app.order_service.entities.Orders;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long id;

    private Integer productId;

    private Integer quantity;

}
