package com.product.demo.service;

import com.product.demo.dto.ProductCreateRequest;
import com.product.demo.dto.ProductResponse;
import com.product.demo.dto.ProductUpdateRequest;
import com.product.demo.entity.Admin;
import com.product.demo.entity.Product;
import com.product.demo.repository.AdminRepository;
import com.product.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    //속성

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    // 상품 생성
    // 0. 컨트롤러에서 데이터 받기
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request, HttpSession session) {

        // 세션에서 로그인 관리자 id 꺼내오기
        Long loginAdminId = (Long) session.getAttribute("LOGIN_ADMIN_ID");

        // 로그인 했는지 확인
        if (loginAdminId == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스 입니다.");
        }

        // 로그인한 관리자 찾기
        Admin loginAdmin = adminRepository.findById(loginAdminId)
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다."));


        //1. 데이터 준비하기
        String newProductName = request.getProductname();
        int newPrice = request.getPrice();
        int newStock = request.getStock();
        Long AdminId = request.getAdminId();

        // 1-2 admin db에서 관리자 조회
        Admin findAdmin = adminRepository.findById(AdminId)
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다."));

        //2. 저장할 엔티티 만들기
        Product newProduct = new Product(newProductName, newPrice, newStock, findAdmin);

        //3.4 db에 저장, 저장한 결과 받기
        Product savedProduct = productRepository.save(newProduct);

        //5. 저장된 데이터 준비
        Long savedProductId = savedProduct.getId();
        String savedProductName = savedProduct.getProductname();
        int savedProductPrice = savedProduct.getPrice();
        int savedProductStock = savedProduct.getStock();
        String savedUsername = savedProduct.getAdmin().getUsername();

        //6. 응답 dto 만들기
        ProductResponse productResponse = new ProductResponse(
                savedProductId, savedProductName, savedProductPrice, savedProductStock, savedUsername);

        //7. 반환하기
        return productResponse;

    }
    // 상품 단건 조회
    @Transactional(readOnly = true)
    //0. 컨트롤러에서 데이터 받기
    public ProductResponse getDetailProduct(Long adminId) {
        //1.db에서 id로 상품 조회
        Product findproduct = productRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        //2.데이터 준비하기
        Long responseId = findproduct.getId();
        String responseProductName = findproduct.getProductname();
        int responseProductPrice = findproduct.getPrice();
        int responseProductStock = findproduct.getStock();
        String responseUsername = findproduct.getAdmin().getUsername();

        //3.응답 dto 만들기
        ProductResponse response = new ProductResponse(
                responseId,
                responseProductName,
                responseProductPrice,
                responseProductStock,
                responseUsername
        );

        //4.반환하기
        return response;
    }

    // 상품 전체 조회
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProductList() {

        // 1. 상품 목록 조회하기
        List<Product> findProductList = productRepository.findAll();

        // 2. dto 만들기 - stream
        List<ProductResponse> productResponseList = findProductList.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getProductname(),
                        product.getPrice(),
                        product.getStock(),
                        product.getAdmin().getUsername()))
                .collect(Collectors.toList());
        return productResponseList;
    }

    // 상품 수정
    @Transactional
    // 1. 컨트롤러에서 데이터 받기
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request, HttpSession session) {

        // 1-1 로그인 관리자 id 조회
        Long loginAdminid = (Long) session.getAttribute("LOGIN_ADMIN_ID");

        // 1-2 로그인 했는지 확인
        if (loginAdminid == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스 입니다.");
        }

        // 2. db에서 id로 상품 조회
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        // 2-1 본인 상품인지 확인하고 아니면 예외처리
        Long productAdminid = findProduct.getAdmin().getId();

        if (!productAdminid.equals(loginAdminid)) {
            throw new IllegalArgumentException("본인이 등록한 상품만 수정이 가능합니다.");
        }

        // 3. 데이터 수정하기
        String updateProductName = request.getProductname();
        int updatePrice = request.getPrice();
        int updateStock = request.getStock();

        findProduct.updateProduct(
                updateProductName,
                updatePrice,
                updateStock
        );

        // 4. 수정한 객체 반환
        Product updateProduct = findProduct;

        // 5. 데이터 준비하기
        Long responseId = updateProduct.getId();
        String responseProductName = updateProduct.getProductname();
        int responsePrice = updateProduct.getPrice();
        int responseStock = updateProduct.getStock();

        Long responseAdminId = updateProduct.getAdmin().getId();
        String responseAdminUsername = updateProduct.getAdmin().getUsername();

        // 6. 응답 dto 만들기
        ProductResponse response = new ProductResponse(
                responseId,
                responseProductName,
                responsePrice,
                responseStock,
                responseAdminUsername
        );
        return response;
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long productId, HttpSession session) {

        // 로그인 관리자 id 조회
        Long loginAdminId = (Long) session.getAttribute("LOGIN_ADMIN_ID");

        // 로그인 했는지 확인
        if (loginAdminId == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스 입니다.");
        }

        //1. db에서 상품 id 조회
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 1-1 본인 상품인지 확인하기
        Long productAdminId = findProduct.getAdmin().getId();

        if (!productAdminId.equals(loginAdminId)) {
            throw new IllegalArgumentException("본인이 등록한 상품만 삭제할 수 있습니다.");
        }

        //2. 데이터 삭제
        productRepository.delete(findProduct);
    }

}

