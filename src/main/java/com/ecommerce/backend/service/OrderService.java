package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.exception.BadRequestException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartItemRepository cartRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public OrderService(CartItemRepository cartRepo,
                        OrderRepository orderRepo,
                        UserRepository userRepo,
                        ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public OrderResponse placeOrder(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<CartItem> cartItems = cartRepo.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cart : cartItems) {

            Product product = cart.getProduct();

            if (cart.getQuantity() > product.getQuantity()) {
                throw new BadRequestException("Not enough stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - cart.getQuantity());
            productRepo.save(product);

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(cart.getQuantity());
            item.setPrice(product.getPrice());

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));

            orderItems.add(item);
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);

        Order savedOrder = orderRepo.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
        }

        savedOrder.setItems(orderItems);
        orderRepo.save(savedOrder);

        cartRepo.deleteAll(cartItems);

        return new OrderResponse(
                savedOrder.getId(),
                total,
                orderItems.stream()
                        .map(i -> i.getProduct().getName())
                        .collect(Collectors.toList())
        );
    }

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
