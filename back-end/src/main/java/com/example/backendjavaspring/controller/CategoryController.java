package com.example.backendjavaspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


public interface CategoryController {

    @GetMapping("/get_all_category")
    ResponseEntity<?> getAllCategory();

    @PostMapping(value = "/add_category")
    ResponseEntity<?> addCategory(@Valid @RequestParam String name);

    @PutMapping(value = "/update_category/{id}")
    ResponseEntity<?> updateCategory(@Valid @RequestParam String name, @PathVariable long id);

    @DeleteMapping(value = "/delete_category/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable long id);

    @GetMapping("/search_category")
    ResponseEntity<?> findCategoryByName(@Validated @RequestParam String name);

    @GetMapping("/get_category/{id}")
    ResponseEntity<?> findCategoryById(@Validated @PathVariable long id);
}
