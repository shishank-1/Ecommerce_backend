package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double price;

    // Many items → one order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Many items → one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}