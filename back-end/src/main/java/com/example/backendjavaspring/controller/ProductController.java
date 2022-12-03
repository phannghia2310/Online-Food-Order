package com.example.backendjavaspring.controller;

import com.example.backendjavaspring.model.dto.ProductBodyRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


public interface ProductController {
    @GetMapping("/get_all_product")
    ResponseEntity<?> getAllProduct();

    @PostMapping(value = "/add_product")
    ResponseEntity<?> addProduct(@Validated @ModelAttribute ProductBodyRequestDTO productBodyRequest);

    @PutMapping(value = "/update_product/{id}")
    ResponseEntity<?> updateProduct(@ModelAttribute ProductBodyRequestDTO productBodyRequest, @PathVariable long id);

    @DeleteMapping(value = "/delete_product/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable long id);

    @GetMapping("/search_product")
    ResponseEntity<?> findProductByFields(@RequestParam HashMap<String, String> map);

    @GetMapping("/get_product/{id}")
    ResponseEntity<?> findProductById(@PathVariable long id);

    @GetMapping("/get_product_new")
    ResponseEntity<?> getProductNew();

    @GetMapping("/get_product_hot")
    ResponseEntity<?> getProductHot();

    @GetMapping("/get_product_by_category/{id}")
    ResponseEntity<?> getProductByCategory(@PathVariable long id);

}
