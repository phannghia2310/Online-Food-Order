package com.example.backendjavaspring.model.dto;


import java.util.List;

public class ResponseDTO {
    private int statusCode;
    private String message;
    private List<?> result;
    
	public ResponseDTO(int statusCode, String message, List<?> result) {
		this.statusCode = statusCode;
		this.message = message;
		this.result = result;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}
    
	
    
}
