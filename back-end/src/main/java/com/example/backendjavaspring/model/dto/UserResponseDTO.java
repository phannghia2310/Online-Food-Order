package com.example.backendjavaspring.model.dto;

import java.sql.Timestamp;

public class UserResponseDTO {
	private String email;
	private String phone;
	private String fullName;
	private String address;
	private String role;
	private int purchaseInvoice;

	private String registrationDate;

	public UserResponseDTO() {
	}

	public UserResponseDTO(String email, String phone, String fullName, String address, String role,
			int purchaseInvoice, String registrationDate) {
		this.email = email;
		this.phone = phone;
		this.fullName = fullName;
		this.address = address;
		this.role = role;
		this.purchaseInvoice = purchaseInvoice;
		this.registrationDate = registrationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getPurchaseInvoice() {
		return purchaseInvoice;
	}

	public void setPurchaseInvoice(int purchaseInvoice) {
		this.purchaseInvoice = purchaseInvoice;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
}
