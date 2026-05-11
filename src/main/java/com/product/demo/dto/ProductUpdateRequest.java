package com.product.demo.dto;

import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    private String productname;
    private int price;
    private int stock;
}
