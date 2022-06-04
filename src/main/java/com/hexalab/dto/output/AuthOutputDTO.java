package com.hexalab.dto.output;

public class AuthOutputDTO {

	private String email;

	private String accessToken;

	public AuthOutputDTO(String email, String accessToken) {
		this.email = email;
		this.accessToken = accessToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
