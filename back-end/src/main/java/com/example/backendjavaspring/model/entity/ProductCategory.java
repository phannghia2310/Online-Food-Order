package com.example.backendjavaspring.model.entity;

import com.example.backendjavaspring.model.compositekey.ProductCategoryKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProductCategory implements Serializable {

    @EmbeddedId
    private ProductCategoryKey productCategoryKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;


    public ProductCategory() {
    }

    public ProductCategory(ProductCategoryKey productCategoryKey, Product product ,Category category) {
        this.productCategoryKey = productCategoryKey;
        this.category = category;
        this.product = product;
    }

    public ProductCategoryKey getProductCategoryKey() {
        return productCategoryKey;
    }

    public void setProductCategoryKey(ProductCategoryKey productCategoryKey) {
        this.productCategoryKey = productCategoryKey;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
