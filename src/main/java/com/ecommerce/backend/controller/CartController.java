package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.*;
import com.ecommerce.backend.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request) {
        return service.addToCart(request);
    }

    @GetMapping("/{userId}")
    public List<CartResponse> getCart(@PathVariable Long userId) {
        return service.getCart(userId);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable Long id) {
        service.removeItem(id);
        return "Item removed";
    }
}