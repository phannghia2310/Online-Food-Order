package com.example.backendjavaspring.model.compositekey;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductCategoryKey implements Serializable {
    @Column
    private long productId;
    @Column
    private long categoryId;

    public ProductCategoryKey() {
    }

    public ProductCategoryKey(long productId, long categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
