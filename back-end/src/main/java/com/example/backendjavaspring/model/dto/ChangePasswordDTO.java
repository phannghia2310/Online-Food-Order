package com.example.backendjavaspring.model.dto;


import javax.validation.constraints.NotBlank;

public class ChangePasswordDTO {
	@NotBlank(message = "'email' cannot be blank")
	private String email;
	@NotBlank(message = "'oldPassword' cannot be blank")
	private String oldPassword;
	@NotBlank(message = "'newPassword' cannot be blank")
	private String newPassword;

	public ChangePasswordDTO() {
	}

	public ChangePasswordDTO(String email, String oldPassword, String newPassword) {
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
