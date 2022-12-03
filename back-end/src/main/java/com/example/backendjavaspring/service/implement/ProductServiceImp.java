package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.model.entity.Product;
import com.example.backendjavaspring.model.entity.ProductCategory;
import com.example.backendjavaspring.repository.ProductRepository;
import com.example.backendjavaspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.backendjavaspring.model.Constants.*;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final ProductCategoryServiceImp productCategoryService;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository, EntityManager entityManager, ProductCategoryServiceImp productCategoryService) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
        this.productCategoryService = productCategoryService;
    }

    boolean isCategory = false;


    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> createListProduct(List<Product> products){
        return productRepository.saveAll(products);
    }

    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> findProductsByName(String name){
//        products.stream()
//                .map(Product::getProductId).collect(Collectors.toList())
//                .forEach(System.out::println);
        return productRepository.findProductsByProductNameContains(name);
    }

    public List<Product> getProductsNew(){
        return productRepository.findTop20ByOrderByCreateDateDesc();
    }

    public List<Product> getProductsHot(){
        return productRepository.findTop20ByOrderByAmountSoldDesc();
    }

    public List<Product> findProductsByPriceFromTo(double from, double to){
        return productRepository.findProductsByProductPriceBetween(from, to);
    }

    public Product findProductById(long id){
        return productRepository.findById(id).orElse(null);
    }

    public long countProduct(){
        return productRepository.count();
    }


    public List<Product> findProductsByFields(HashMap<String,String> map){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        Predicate[] arrPredicate = addQueryCondition(criteriaBuilder, root, map);
        criteriaQuery.select(root).where(arrPredicate);
        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

        List<Product> products = query.getResultList();
        List<Product> result = new ArrayList<>();
        if(isCategory){
            long categoryId = Long.parseLong(map.get(SEARCH_FIELD_PRODUCT_TYPE));
            List<ProductCategory> productCategories = productCategoryService.findProductCategoryByCategoryId(categoryId);
            for(ProductCategory productCategory : productCategories){
                for(Product product : products){
                    if(productCategory.getProduct().getProductId() == product.getProductId()){
                        result.add(product);
                    }
                }
            }
            isCategory = false;
            return result;
        }
       return products;
    }

    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    private Predicate[] addQueryCondition(CriteriaBuilder criteriaBuilder, Root<Product> root, HashMap<String, String> map) {
        map.values().removeAll(Collections.singleton(null));
        map.values().removeAll(Collections.singleton(""));
        List<Predicate> predicates = new ArrayList<>();
        for (String key : map.keySet()) {
            switch (key) {
                case SEARCH_FIELD_PRODUCT_AMOUNT_FROM:
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(PRODUCT_AMOUNT), Integer.parseInt(map.get(key).trim())));
                    break;
                case SEARCH_FIELD_PRODUCT_AMOUNT_TO:
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(PRODUCT_AMOUNT), Integer.parseInt(map.get(key).trim())));
                    break;
                case SEARCH_FIELD_PRODUCT_PRICE_FROM:
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(PRODUCT_PRICE), Double.parseDouble(map.get(key).trim())));
                    break;
                case SEARCH_FIELD_PRODUCT_PRICE_TO:
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(PRODUCT_PRICE), Double.parseDouble(map.get(key).trim())));
                    break;
                case SEARCH_FIELD_PRODUCT_NAME:
                    String keysearch = removeAccent(map.get(key).trim());
                    predicates.add(criteriaBuilder.like(root.get(PRODUCT_NAME), "%" + keysearch + "%"));
                    break;
                case SEARCH_FIELD_PRODUCT_TYPE:
                    isCategory = true;
                    break;
            }
        }
        Predicate[] predicatesArr = new Predicate[predicates.size()];
        return predicates.toArray(predicatesArr);
    }



}

