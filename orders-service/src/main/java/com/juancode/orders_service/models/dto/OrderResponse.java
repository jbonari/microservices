package com.juancode.orders_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public record OrderResponse (Long id,String orderNumber, List<OrderItemResponse> orderItems){


}
