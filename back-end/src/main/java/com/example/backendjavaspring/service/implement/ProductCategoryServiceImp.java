package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.model.compositekey.ProductCategoryKey;
import com.example.backendjavaspring.model.entity.ProductCategory;
import com.example.backendjavaspring.repository.ProductCategoryRepository;
import com.example.backendjavaspring.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService {
    @Autowired
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImp(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory createProductCategory(ProductCategory productCategory){
        return productCategoryRepository.save(productCategory);
    }

    public synchronized void deleteProductCategoryById(ProductCategoryKey id){
        productCategoryRepository.deleteById(id);
    }

    public ProductCategory findProductCategoryById(){
        return null;
    }

    public ProductCategory updateProductCategory(ProductCategory productCategory){
        return productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> findProductCategoryByProductId(long id){
        return productCategoryRepository.findProductCategorysByProductProductId(id);
    }

    public List<ProductCategory> findProductCategoryByCategoryId(long id){
        return productCategoryRepository.findProductCategorysByCategoryCategoryId(id);
    }

}
