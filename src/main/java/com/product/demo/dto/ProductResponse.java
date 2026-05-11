package com.product.demo.dto;

import lombok.Getter;

@Getter
public class ProductResponse {

    // 속성
    private Long id;
    private String productname;
    private int price;
    private int stock;
    private String username;

    private Long adminId;

    public ProductResponse(
            Long id,
            String productname,
            int price,
            int stock,
            String username
    ) {
        this.id = id;
        this.productname = productname;
        this.price = price;
        this.stock = stock;
        this.adminId = adminId;
        this.username = username;
    }

}
