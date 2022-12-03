package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.model.entity.Category;
import com.example.backendjavaspring.repository.CategoriesRepository;
import com.example.backendjavaspring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoryServiceImp(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Category createCategories(Category category){
        return categoriesRepository.save(category);
    }

    public List<Category> getAllCategorie(){
        return categoriesRepository.findAll();
    }

    public long countCategoties(){
        return categoriesRepository.count();
    }

    public void deleteCategoriesById(long id){
        categoriesRepository.deleteById(id);
    }

    public Category updateCategories(Category category){
        return  categoriesRepository.save(category);
    }

    public List<Category> findCategoriesById(long[] ids){
        if(ids == null){
            return null;
        }

        List<Category> categories = new ArrayList<>();
        for(long id : ids){
            categoriesRepository.findById(id).ifPresent(categories::add);
        }

        if(categories.isEmpty()){
            return null;
        }

        return categories;
    }

    public Category findCategoriesById(long id){
        return categoriesRepository.findById(id).orElse(null);
    }

    public List<Category> findCategoriesByName(String name){
        return categoriesRepository.findCategoriesByCategoryNameContaining(name);
    }

    public boolean isCategoriesByNameExist(String name){
        return categoriesRepository.findCategoryByCategoryNameEquals(name) != null;
    }

}
