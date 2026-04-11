package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductRequest;
import com.ecommerce.backend.dto.ProductResponse;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Marks this class as a Service layer component
// Service layer contains business logic
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse saveProduct(ProductRequest request) {
        Product product = new Product(null,
                request.getName(),
                request.getPrice(),
                request.getQuantity());

        Product saved = repository.save(product);

        return new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getQuantity()
        );
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getQuantity()
        );
    }
}