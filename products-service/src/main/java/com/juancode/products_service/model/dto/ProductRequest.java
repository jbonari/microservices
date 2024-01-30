package com.juancode.products_service.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String sku;
    private String name;
    private String description;
    private Double price;
    private Boolean status;


}
