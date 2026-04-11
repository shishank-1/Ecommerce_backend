package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.*;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartItemRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public CartService(CartItemRepository cartRepo,
                       UserRepository userRepo,
                       ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    // ➕ Add to Cart
    public CartResponse addToCart(CartRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = new CartItem(
                null,
                request.getQuantity(),
                user,
                product
        );

        CartItem saved = cartRepo.save(item);

        return new CartResponse(
                saved.getId(),
                product.getName(),
                saved.getQuantity(),
                product.getPrice()
        );
    }

    // 📦 View Cart
    public List<CartResponse> getCart(Long userId) {

        return cartRepo.findByUserId(userId)
                .stream()
                .map(item -> new CartResponse(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice()
                ))
                .collect(Collectors.toList());
    }

    // 🗑 Remove Item
    public void removeItem(Long cartItemId) {
        cartRepo.deleteById(cartItemId);
    }
}