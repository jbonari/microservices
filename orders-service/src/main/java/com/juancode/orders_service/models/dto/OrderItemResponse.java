package com.juancode.orders_service.models.dto;



public record OrderItemResponse(Long id, String sku, Double price,Long quantity) {
}
