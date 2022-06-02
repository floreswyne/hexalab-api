package com.hexalab.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5405807362027288212L;
	
	private final StandartError errorBody;
	
	public UserNotFoundException(String userId) {
		errorBody = new StandartError();
		errorBody.setTimestamp(LocalDateTime.now());
		errorBody.setStatus(HttpStatus.NOT_FOUND.value());
		errorBody.setError("Transfer not found!");
		errorBody.setMessage("User with ID: " + userId + " cannot be found!");
		errorBody.setPath("test");
	}

	public StandartError getErrorBody() {
		return errorBody;
	}

}
