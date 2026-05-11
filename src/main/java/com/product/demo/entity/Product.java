package com.product.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Product {

    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품 이름
    private String productname;

    // 상품 가격
    private int price;

    // 재고 수량
    private int stock;

    // 연관관계 설정
    // 상품이 관리자를 참조 - 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Product(
            String productname,
            int price,
            int stock,
            Admin admin
    ) {
        this.productname = productname;
        this.price = price;
        this.stock = stock;
        this.admin = admin;
    }

    public Product(String newProductName, int newPrice, int newStock) {
    }

    // 상품 수정
    public void updateProduct(
            String productname,
            int price,
            int stock
    ) {
        this.productname = productname;
        this.price = price;
        this.stock = stock;
    }
}
