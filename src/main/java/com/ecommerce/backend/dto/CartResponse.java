package com.ecommerce.backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class CartResponse {

    private Long id;
    private String productName;
    private int quantity;
    private double price;
}