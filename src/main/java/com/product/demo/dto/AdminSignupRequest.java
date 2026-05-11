package com.product.demo.dto;

import lombok.Getter;

// 회원가입 요청 dto
@Getter
public class AdminSignupRequest {

    private String username;
    private String email;
    private String password;


}
