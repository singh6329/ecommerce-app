package com.app.shipping_service.services;

import com.app.shipping_service.dtos.ShippingDto;
import com.app.shipping_service.entities.Shipping;
import com.app.shipping_service.entities.enums.CourierService;
import com.app.shipping_service.entities.enums.ShippingStatus;
import com.app.shipping_service.repositories.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingRepository shippingRepository;
    private final ModelMapper modelMapper;

    public ShippingDto shipOrder(ShippingDto shippingDto) {
        Shipping shipping = modelMapper.map(shippingDto, Shipping.class);
        shipping.setShippingStatus(ShippingStatus.DISPATCHED);
        shipping.setCourierService(CourierService.DHL);
        Shipping savedShipping = shippingRepository.save(shipping);
        return modelMapper.map(savedShipping,ShippingDto.class);

    }

    public void cancelShipping(ShippingDto shippingDto) {
        Shipping shipping = shippingRepository.findByOrderId(shippingDto.getOrderId()).orElseThrow(()-> new RuntimeException("Shipping id doesn't exists!"));
        if(shipping.getShippingStatus().equals(ShippingStatus.CANCELLED))
        throw new RuntimeException("Shipping already been cancelled fo this orderId!");
        shipping.setShippingStatus(ShippingStatus.CANCELLED);
        shippingRepository.save(shipping);
    }
}
