package com.juancode.inventory_service.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private Long quantity;
}
