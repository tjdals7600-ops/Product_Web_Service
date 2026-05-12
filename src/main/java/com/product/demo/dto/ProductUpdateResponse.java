package com.product.demo.dto;

import lombok.Getter;

@Getter
public class ProductUpdateResponse {
    private Long id;
    private String productname;
    private int price;
    private int stock;

    public ProductUpdateResponse(
            Long id,
            String productname,
            int price,
            int stock
    ) {
        this.id = id;
        this.productname = productname;
        this.price = price;
        this.stock = stock;
    }
}
