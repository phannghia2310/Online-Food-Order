package com.example.backendjavaspring.controller.implement;

import com.example.backendjavaspring.controller.CategoryController;
import com.example.backendjavaspring.model.dto.ResponseDTO;
import com.example.backendjavaspring.model.entity.Category;
import com.example.backendjavaspring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerImp implements CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryControllerImp(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public ResponseEntity<?> getAllCategory() {
        List<Category> categoriesResult = categoryService.getAllCategorie();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200,"SUCCESSFULLY!",categoriesResult));
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> addCategory(String name) {
        boolean checkName = categoryService.isCategoriesByNameExist(name);

        if(name.isEmpty() || checkName){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404,"CATERORIES NAME MUST NOT BE BLANK!",null));
        }
        List<Category> categoriesResult = new ArrayList<>();
        try {
            Category category = new Category(name);
            Category result = categoryService.createCategories(category);
            if(result != null){
                categoriesResult.add(result);
            }else {
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404,"ADD CATEGORIES FAIL!",null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200,"ADD CATEGORIES SUCCESSFULLY!",categoriesResult));
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> updateCategory(String name, long id) {
        Category category = categoryService.findCategoriesById(id);
        boolean checkName = categoryService.isCategoriesByNameExist(name);
        List<Category> categoriesResult = new ArrayList<>();
        if(category == null || name.isEmpty() || checkName){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404,"CATEGORIES NAME EXIST OR MUST NOT BE BLANK OR NOT FOUND CATEGORIES ID = " + id ,null));
        }
        category.setCategoryName(name);
        Category result = categoryService.updateCategories(category);
        categoriesResult.add(result);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200,"UPDATE CATEGORUES SUCCESSFULLY!",categoriesResult));
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> deleteCategory(long id) {
        try {
            categoryService.deleteCategoriesById(id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404,"NOT FOUND CATEGORIES ID = " + id ,null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200,"DELETE CATEGORUES SUCCESSFULLY!", new ArrayList<>()));
    }

    @Override
    public ResponseEntity<?> findCategoryByName(String name) {
        List<Category> categoriesResult = categoryService.findCategoriesByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200,"SUCCESSFULLY!", categoriesResult));

    }

    @Override
    public ResponseEntity<?> findCategoryById(long id) {
        Category product = categoryService.findCategoriesById(id);
        List<Category> categoriesResult = new ArrayList<>();
        if(product != null){
            categoriesResult.add(product);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "SUCCESSFULLY!", categoriesResult));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(404, "NOT FOUND CATEGORIES ID = " + id, categoriesResult));
    }
}
