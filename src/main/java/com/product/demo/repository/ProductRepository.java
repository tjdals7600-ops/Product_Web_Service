package com.product.demo.repository;

import com.product.demo.entity.Admin;
import com.product.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByAdmin(Admin admin);
}
