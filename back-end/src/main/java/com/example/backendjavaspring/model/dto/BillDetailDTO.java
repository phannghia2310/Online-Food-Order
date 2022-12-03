package com.example.backendjavaspring.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.math.BigDecimal;
import java.util.List;


public class BillDetailDTO {
    @JsonProperty(index = 0)
    private long id;
    @JsonProperty(index = 1)
    private String purchaseDate;
    @JsonProperty(index = 2)
    private BigDecimal totalPrice;
    @JsonProperty(index = 3)
    private int totalAmount;
	@JsonProperty(index = 4)
    private BigDecimal feeShip;
    @JsonProperty(namespace = "user", index = 5)
    private UserResponseDTO userResponse;
    @JsonProperty(namespace = "products", index = 6)
    private List<ProductResponseDTO> productResponse;
	@JsonProperty(index = 7)
    private String status;

    public BillDetailDTO() {
    }
    
	public BillDetailDTO(long id, List<ProductResponseDTO> productResponse, int totalAmount,
			UserResponseDTO userResponse, String purchaseDate, BigDecimal totalPrice) {
		this.id = id;
		this.productResponse = productResponse;
		this.totalAmount = totalAmount;
		this.userResponse = userResponse;
		this.purchaseDate = purchaseDate;
		this.totalPrice = totalPrice;
	}

	public BigDecimal getFeeShip() {
		return feeShip;
	}

	public void setFeeShip(BigDecimal feeShip) {
		this.feeShip = feeShip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<ProductResponseDTO> getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(List<ProductResponseDTO> productResponse) {
		this.productResponse = productResponse;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public UserResponseDTO getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(UserResponseDTO userResponse) {
		this.userResponse = userResponse;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
    
    
}
