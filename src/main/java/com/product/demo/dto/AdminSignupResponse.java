package com.product.demo.dto;

import lombok.Getter;

@Getter
public class AdminSignupResponse {
    private Long id;
    private String username;
    private String email;

    public AdminSignupResponse(
            Long id,
            String username,
            String email
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
