package com.example.backendjavaspring.model.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billId;
    @Column(nullable = false)
    private String purchaserEmail;
    @Column(nullable = false)
    private Timestamp purchaseDate;
	@Column(nullable = false)
    private BigDecimal price;
	@Column(nullable = false)
	private String status;
	private BigDecimal feeShip;

	public Bill() {
    }

    public Bill(String purchaserEmail){
        this.purchaseDate = new Timestamp(System.currentTimeMillis());
        this.purchaserEmail = purchaserEmail;
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

	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getFeeShip() {
		return feeShip;
	}

	public void setFeeShip(BigDecimal feeShip) {
		this.feeShip = feeShip;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
    
}
