package com.product.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Admin {

    // 속성

    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 관리자 아이디
    private String username;

    // 관리자 이메일
    private String email;

    // 관리자 비밀번호
    private String password;



    public Admin(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
