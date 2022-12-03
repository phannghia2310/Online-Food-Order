package com.example.backendjavaspring.repository;

import com.example.backendjavaspring.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByCategoryNameContaining(String name);
    Category findCategoryByCategoryNameEquals(String name);
}
