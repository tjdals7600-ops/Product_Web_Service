package com.product.demo.repository;

import com.product.demo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    // username으로 관리자 조회하기
    Optional<Admin> findByUsername(String username);

    // 이메일 중복 체크
    boolean existsByEmail(String email);
}
