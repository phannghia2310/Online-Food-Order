package com.example.backendjavaspring.repository;

import com.example.backendjavaspring.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByProductNameContains(String productName);
    List<Product> findProductsByProductPriceBetween(double from, double to);
    List<Product> findTop20ByOrderByCreateDateDesc();
    List<Product> findTop20ByOrderByAmountSoldDesc();
}
