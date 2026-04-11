package com.ecommerce.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @Positive(message = "Price must be positive")
    private double price;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
}