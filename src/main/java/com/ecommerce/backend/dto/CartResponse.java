package com.ecommerce.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartResponse {

    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
