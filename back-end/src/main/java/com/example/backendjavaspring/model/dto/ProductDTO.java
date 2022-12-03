package com.example.backendjavaspring.model.dto;

import com.example.backendjavaspring.model.entity.Category;
import com.example.backendjavaspring.model.entity.Image;


import java.sql.Timestamp;
import java.util.List;


public class ProductDTO {
    private long productId;
    private String productName;
    private double productPrice;
    private String productDes;
	private double productMass;
    private int productAmount;
    private int amountSold;
    private Timestamp createDate;
    private List<Image> images;
    private List<Category> categories;

    public ProductDTO() {
    }

	public double getProductMass() {
		return productMass;
	}

	public void setProductMass(double productMass) {
		this.productMass = productMass;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDes() {
		return productDes;
	}

	public void setProductDes(String productDes) {
		this.productDes = productDes;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public int getAmountSold() {
		return amountSold;
	}

	public void setAmountSold(int amountSold) {
		this.amountSold = amountSold;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
    
    
}
