package com.product.demo.controller;

import com.product.demo.dto.AdminCreateRequest;
import com.product.demo.dto.AdminResponse;
import com.product.demo.dto.AdminUpdateRequest;
import com.product.demo.dto.LoginRequest;
import com.product.demo.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    //속성
    private final AdminService adminService;

    //생성자
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 관리자 생성 (회원 가입)
    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(@RequestBody AdminCreateRequest request) {
        AdminResponse adminResponseDto = adminService.createAdmin(request);
        ResponseEntity<AdminResponse> response = new ResponseEntity<>(adminResponseDto, HttpStatus.CREATED);
        return response;
    }

    // 관리자 단건 조회
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getDetailAdmin(@PathVariable Long adminId) {
        AdminResponse adminGetResponseDto = adminService.getDetailAdmin(adminId);
        ResponseEntity<AdminResponse> response = new ResponseEntity<>(adminGetResponseDto, HttpStatus.OK);
        return response;

    }

    // 관리자 전체 조회
    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAllAdminList() {
        List<AdminResponse> adminGetAllResponseDto = adminService.getAllAdminList();
        ResponseEntity<List<AdminResponse>> response = new ResponseEntity<>(adminGetAllResponseDto, HttpStatus.OK);
        return response;
    }

    // 관리자 수정
    @PatchMapping("/{adminId}")
    public ResponseEntity<AdminResponse> updateAdmin(@PathVariable Long adminId, @RequestBody AdminUpdateRequest request) {
        AdminResponse adminUpdateResponse = adminService.updateAdmin(adminId, request);
        ResponseEntity<AdminResponse> response = new ResponseEntity<>(adminUpdateResponse, HttpStatus.OK);
        return response;
    }

    // 관리자 삭제
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteAdmin(adminId);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }

    // 관리자 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpSession session) {
        adminService.login(request, session);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }

    // 관리자 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        adminService.logout(session);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.OK);
    }

}

