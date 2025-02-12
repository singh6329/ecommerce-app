package com.app.order_service.dtos;

import com.app.order_service.entities.OrderItem;
import com.app.order_service.entities.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Long id;

    private Double totalPrice;

    private List<OrderRequestItemDto> items;

    private OrderStatus orderStatus;
}
