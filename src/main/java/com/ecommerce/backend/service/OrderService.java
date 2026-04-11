package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartItemRepository cartRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    public OrderService(CartItemRepository cartRepo,
                        OrderRepository orderRepo,
                        UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    // 🧾 Place Order
    public OrderResponse placeOrder(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartRepo.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cart : cartItems) {

            OrderItem item = new OrderItem();
            item.setProduct(cart.getProduct());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getProduct().getPrice());

            total += cart.getQuantity() * cart.getProduct().getPrice();

            orderItems.add(item);
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);

        // save order first
        Order savedOrder = orderRepo.save(order);

        // link order items
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
        }

        savedOrder.setItems(orderItems);
        orderRepo.save(savedOrder);

        // 🧹 Clear cart
        cartRepo.deleteAll(cartItems);

        return new OrderResponse(
                savedOrder.getId(),
                total,
                orderItems.stream()
                        .map(i -> i.getProduct().getName())
                        .collect(Collectors.toList())
        );
    }

    // 📦 Order History
    public List<OrderResponse> getOrders(Long userId) {

        return orderRepo.findByUserId(userId)
                .stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getTotalAmount(),
                        order.getItems()
                                .stream()
                                .map(i -> i.getProduct().getName())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}