package com.ecommerce.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private BigDecimal totalAmount;
    private List<String> products;
}
