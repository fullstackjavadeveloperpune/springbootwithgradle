package com.fullstack.repository;

import com.fullstack.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Custom method
    List<Product> findByProductName(String productName);
}
