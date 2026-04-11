package com.ecommerce.backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private int quantity;
}