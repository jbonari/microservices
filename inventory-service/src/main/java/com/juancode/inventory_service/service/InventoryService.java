package com.juancode.inventory_service.service;

import com.juancode.inventory_service.models.dto.OrderItemRequest;
import com.juancode.inventory_service.models.entities.Inventory;
import com.juancode.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.juancode.inventory_service.models.dto.BaseResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean inStock(String sku) {
        var inventory=inventoryRepository.findBySku(sku);
        return inventory.filter(value->value.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {

        var errorList=new ArrayList<>();
        List<String> skus=orderItems.stream().map(OrderItemRequest::getSku).toList();

        List<Inventory> inventoryList=inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem->{
            var inventory=inventoryList.stream().filter(value->value.getSku().equals(orderItem.getSku())).findFirst();
            if (inventory.isEmpty()){
                errorList.add("Product with sku "+ orderItem.getSku()+ " doesn't exist");
            } else if (inventory.get().getQuantity()<orderItem.getQuantity()) {
                errorList.add("Product with sku "+orderItem.getSku()+" has insufficient quantity");
            }
        });
        return errorList.size()>0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
}
