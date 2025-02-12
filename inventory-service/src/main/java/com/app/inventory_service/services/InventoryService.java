package com.app.inventory_service.services;

import com.app.inventory_service.dtos.InventoryDto;
import com.app.inventory_service.dtos.OrderRequestDto;
import com.app.inventory_service.dtos.OrderRequestItemDto;
import com.app.inventory_service.entities.Inventory;
import com.app.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public List<InventoryDto> getAllInventory()
    {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream().map(inventory -> modelMapper.map(inventory,InventoryDto.class))
                .toList();
    }

    public InventoryDto getInventoryById(Long id)
    {
       Inventory inventory = inventoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Id doesn't exists!"));
        return modelMapper.map(inventory,InventoryDto.class);
    }


    public Double reduceStock(OrderRequestDto orderRequestDto) {
        Double totalPrice = 0D;
        for (OrderRequestItemDto orderRequestItemDto:orderRequestDto.getItems())
        {
            Inventory inventory = inventoryRepository.findById(Long.valueOf(orderRequestItemDto.getProductId())).orElseThrow(()->new RuntimeException("Item doesn't exists!"));
            if(inventory.getQuantity()<orderRequestItemDto.getQuantity())
                throw new RuntimeException("Cannot have sufficient quantity of productId: "+orderRequestItemDto.getId()+" to fulfil the request!");
            inventory.setQuantity(inventory.getQuantity()-orderRequestItemDto.getQuantity());
            totalPrice += inventory.getPrice()*orderRequestItemDto.getQuantity();
            inventoryRepository.save(inventory);
        }
        return totalPrice;
    }

    public void addCancelledStocks(OrderRequestDto orderRequestDto) {
        for (OrderRequestItemDto orderRequestItemDto: orderRequestDto.getItems())
        {
            Inventory inventory = inventoryRepository.findById(Long.valueOf(orderRequestItemDto.getProductId())).orElseThrow(()-> new RuntimeException("Product doesn't exists with id: "+orderRequestItemDto.getProductId()));
            inventory.setQuantity(inventory.getQuantity()+orderRequestItemDto.getQuantity());
            inventoryRepository.save(inventory);
        }
    }
}
