package com.juancode.orders_service.service;

import com.juancode.orders_service.models.dto.*;
import com.juancode.orders_service.models.entities.Order;
import com.juancode.orders_service.models.entities.OrderItems;
import com.juancode.orders_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){

        //check inventory
        BaseResponse result=this.webClientBuilder.build()
                .post()
                .uri("lb://inventory-service/api/inventory/in-stock")

                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if (result!=null && result.hasErrors()){
            Order order=new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream()
                                                            .map(orderItemRequest -> mapOrderItemRequesttoOrderItem(orderItemRequest,order))
                                                            .toList());

            this.orderRepository.save(order);

        }else{
            throw new IllegalArgumentException("some of the products are not in stock");
        }

    }

    public List<OrderResponse>  getAllOrders(){
        List<Order> orders=orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
    }

    private OrderItemResponse mapToOrderItemRequest(OrderItems orderItems) {
        return new OrderItemResponse(
                orderItems.getId(),
                orderItems.getSku(),
                orderItems.getPrice(),
                orderItems.getQuantity()
        );
    }

    private OrderItems mapOrderItemRequesttoOrderItem(OrderItemRequest orderItemRequest, Order order) {

        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }


}
