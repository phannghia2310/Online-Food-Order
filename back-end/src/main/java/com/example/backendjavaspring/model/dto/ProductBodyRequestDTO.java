package com.example.backendjavaspring.model.dto;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ProductBodyRequestDTO implements Serializable {
    @NotBlank(message = "Product 'name' cannot be blank")
    private String name;
    private String des;
    @NonNull
    private double price;
    @NonNull
    private int amount;
    private long[] categoryId;
    private MultipartFile[] imageFiles;
    private long[] imageId;
    
    
	public ProductBodyRequestDTO() {
	}
	
	public ProductBodyRequestDTO(String name, String des,double price, int amount, long[] categoryId, MultipartFile[] imageFiles, long[] imageId) {
		this.name = name;
		this.des = des;
		this.price = price;
		this.amount = amount;
		this.categoryId = categoryId;
		this.imageFiles = imageFiles;
		this.imageId = imageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public long[] getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long[] categoryId) {
		this.categoryId = categoryId;
	}
	public MultipartFile[] getImageFiles() {
		return imageFiles;
	}
	public void setImageFiles(MultipartFile[] imageFiles) {
		this.imageFiles = imageFiles;
	}
	public long[] getImageId() {
		return imageId;
	}
	public void setImageId(long[] imageId) {
		this.imageId = imageId;
	}
    
    
}
