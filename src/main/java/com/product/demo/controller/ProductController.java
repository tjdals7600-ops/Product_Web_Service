package com.product.demo.controller;

import com.product.demo.dto.ProductCreateRequest;
import com.product.demo.dto.ProductResponse;
import com.product.demo.dto.ProductUpdateRequest;
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
    public ResponseEntity<ProductResponse> getDetailProduct(@PathVariable Long productId) {
        ProductResponse productGetResponseDto = productService.getDetailProduct(productId);
        ResponseEntity<ProductResponse> response = new ResponseEntity<>(productGetResponseDto, HttpStatus.OK);
        return response;
    }

    // 상품 전체 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProductList() {
        List<ProductResponse> productGetAllResponseDto = productService.getAllProductList();
        ResponseEntity<List<ProductResponse>> response = new ResponseEntity<>(productGetAllResponseDto, HttpStatus.OK);
        return response;
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request, HttpSession session) {
        ProductResponse productUpdateResponse = productService.updateProduct(productId, request, session);
        ResponseEntity<ProductResponse> response = new ResponseEntity<>(productUpdateResponse, HttpStatus.OK);
        return response;
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId, HttpSession session) {
        productService.deleteProduct(productId, session);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }
}
