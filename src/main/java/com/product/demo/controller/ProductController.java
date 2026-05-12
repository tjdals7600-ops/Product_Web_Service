package com.product.demo.controller;

import com.product.demo.dto.*;
import com.product.demo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
    // 속성
    private final ProductService productService;

   // 생성자
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request, HttpSession session) {
        ProductResponse productResponseDto = productService.createProduct(request, session);
        ResponseEntity<ProductResponse> response = new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        return response;
    }

    // 상품 단건 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getDetailProduct(@PathVariable Long productId) {
        ProductDetailResponse productGetResponseDto = productService.getDetailProduct(productId);
        ResponseEntity<ProductDetailResponse> detailresponse = new ResponseEntity<>(productGetResponseDto, HttpStatus.OK);
        return detailresponse;
    }

    // 상품 전체 조회
    @GetMapping
    public ResponseEntity<List<ProductListResponse>> getAllProductList() {
        List<ProductListResponse> productGetAllResponseDto = productService.getAllProductList();
        ResponseEntity<List<ProductListResponse>> listresponse = new ResponseEntity<>(productGetAllResponseDto, HttpStatus.OK);
        return listresponse;
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductUpdateResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request, HttpSession session) {
        ProductUpdateResponse productUpdateResponse = productService.updateProduct(productId, request, session);
        ResponseEntity<ProductUpdateResponse> updateresponse = new ResponseEntity<>(productUpdateResponse, HttpStatus.OK);
        return updateresponse;
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId, HttpSession session) {
        productService.deleteProduct(productId, session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
