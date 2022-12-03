package com.example.backendjavaspring.model.dto;


import com.example.backendjavaspring.model.entity.Product;

public class ProductResponseDTO {
    private Product product;
    private int amountPurchased;
    
	public ProductResponseDTO(Product product, int amountPurchased) {
		this.product = product;
		this.amountPurchased = amountPurchased;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmountPurchased() {
		return amountPurchased;
	}

	public void setAmountPurchased(int amountPurchased) {
		this.amountPurchased = amountPurchased;
	}
    
    
}
