package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.compositekey.ProductCategoryKey;
import com.example.backendjavaspring.model.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory createProductCategory(ProductCategory productCategory);
    void deleteProductCategoryById(ProductCategoryKey id);
    ProductCategory updateProductCategory(ProductCategory productCategory);
    List<ProductCategory> findProductCategoryByProductId(long id);
    List<ProductCategory> findProductCategoryByCategoryId(long id);
}
