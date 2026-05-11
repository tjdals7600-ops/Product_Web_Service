package com.product.demo.dto;

import lombok.Getter;

// 상품 생성, 수정 요청 dto
@Getter
public class ProductCreateRequest {

    private String productname;
    private int price;
    private int stock;

    // 상품 등록 관리자 id
    private Long adminId;


    public ProductCreateRequest(String productname, int price, int stock, Long adminId) {
     this.productname = productname;
     this.price = price;
     this.stock = stock;
     this.adminId = adminId;
    }
}
