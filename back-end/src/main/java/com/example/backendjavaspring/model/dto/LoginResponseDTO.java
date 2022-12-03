package com.example.backendjavaspring.model.dto;


public class LoginResponseDTO {
    private String token;
    private final String tokenType = "Bearer";

    public LoginResponseDTO(String accessToken) {
        this.token = accessToken;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}
    
    

}
