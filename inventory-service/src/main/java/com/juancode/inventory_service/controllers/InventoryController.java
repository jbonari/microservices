package com.juancode.inventory_service.controllers;

import com.juancode.inventory_service.models.dto.BaseResponse;
import com.juancode.inventory_service.models.dto.OrderItemRequest;
import com.juancode.inventory_service.repository.InventoryRepository;
import com.juancode.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean inStock(@PathVariable("sku") String sku){
        return inventoryService.inStock(sku);
    }

    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInStock(@RequestBody List<OrderItemRequest> orderItems){
        return inventoryService.areInStock(orderItems);
    }

}
