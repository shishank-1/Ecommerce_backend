package com.ecommerce.backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String email;
}