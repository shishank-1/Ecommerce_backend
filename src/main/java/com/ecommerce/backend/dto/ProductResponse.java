package com.ecommerce.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
