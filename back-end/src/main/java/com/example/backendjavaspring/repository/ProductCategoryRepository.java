package com.example.backendjavaspring.repository;

import com.example.backendjavaspring.model.entity.ProductCategory;
import com.example.backendjavaspring.model.compositekey.ProductCategoryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryKey> {
    List<ProductCategory> findProductCategorysByProductProductId(long productId);
    List<ProductCategory> findProductCategorysByCategoryCategoryId(long categoriesId);
}
