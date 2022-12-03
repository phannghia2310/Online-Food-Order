package com.example.backendjavaspring.model.entity;


import javax.persistence.*;

@Entity(name = "bill_info")

public class BillInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billInfoId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bill bill;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private int amount;

    public BillInfo() {
    }

	public long getBillInfoId() {
		return billInfoId;
	}

	public void setBillInfoId(long billInfoId) {
		this.billInfoId = billInfoId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
    
    
}
