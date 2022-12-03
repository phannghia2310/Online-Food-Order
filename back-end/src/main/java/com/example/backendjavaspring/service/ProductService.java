package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.entity.Product;

import java.util.HashMap;
import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();
    Product createProduct(Product product);
    List<Product> createListProduct(List<Product> products);
    void deleteProduct(long id);
    Product updateProduct(Product product);
    List<Product> getProductsNew();
    List<Product> getProductsHot();
    List<Product> findProductsByPriceFromTo(double from, double to);
    Product findProductById(long id);
    List<Product> findProductsByFields(HashMap<String,String> map);
    long countProduct();
    List<Product> findProductsByName(String name);
}
