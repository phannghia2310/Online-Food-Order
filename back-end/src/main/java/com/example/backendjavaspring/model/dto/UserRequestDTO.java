package com.example.backendjavaspring.model.dto;


import javax.validation.constraints.NotBlank;

public class UserRequestDTO {
    @NotBlank(message = "User 'email' cannot be blank")
    private String email;
    @NotBlank(message = "User 'email' cannot be blank")
    private String password;
    @NotBlank(message = "User 'email' cannot be blank")
    private String fullName;
    @NotBlank(message = "User 'fullName' cannot be blank")
    private String phone;
    @NotBlank(message = "User 'address' cannot be blank")
    private String address;

    public UserRequestDTO() {
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    

}
