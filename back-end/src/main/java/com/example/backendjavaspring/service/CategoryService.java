package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategories(Category category);

    List<Category> getAllCategorie();

    long countCategoties();

    void deleteCategoriesById(long id);

    Category updateCategories(Category category);

    List<Category> findCategoriesById(long[] ids);

    Category findCategoriesById(long id);

    List<Category> findCategoriesByName(String name);

    boolean isCategoriesByNameExist(String name);

}
