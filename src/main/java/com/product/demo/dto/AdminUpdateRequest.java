package com.product.demo.dto;

import lombok.Getter;

@Getter
 public class AdminUpdateRequest {
    //속성
    private Long id;
    private String username;
    private String email;


    // 생성자
    public AdminUpdateRequest(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
