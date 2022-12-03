package com.example.backendjavaspring.model.dto;


import java.math.BigDecimal;

public class BillDTO {
    private long billId;
    private String purchaserEmail;
    private String purchaserName;
    private String purchaseDate;
    private BigDecimal price;
	private BigDecimal feeShip;
	private String status;
    
    public BillDTO(long billId, String purchaserEmail, String purchaserName, String purchaseDate, BigDecimal price, BigDecimal feeShip, String status) {
		this.billId = billId;
		this.purchaserEmail = purchaserEmail;
		this.purchaserName = purchaserName;
		this.purchaseDate = purchaseDate;
		this.price = price;
		this.feeShip = feeShip;
		this.status = status;
	}

	public BillDTO() {
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

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	public String getPurchaserEmail() {
		return purchaserEmail;
	}

	public void setPurchaserEmail(String purchaserEmail) {
		this.purchaserEmail = purchaserEmail;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
    
}
