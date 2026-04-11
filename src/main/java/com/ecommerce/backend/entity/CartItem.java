package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    // Many cart items → one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many cart items → one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}