package com.ecommerce.backend.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private double totalAmount;
    private List<String> products;
}