package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/{userId}")
    public OrderResponse placeOrder(@PathVariable Long userId) {
        return service.placeOrder(userId);
    }

    @GetMapping("/{userId}")
    public List<OrderResponse> getOrders(@PathVariable Long userId) {
        return service.getOrders(userId);
    }
}