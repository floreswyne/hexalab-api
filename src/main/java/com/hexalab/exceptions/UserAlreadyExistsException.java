package com.hexalab.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -7186443880620020979L;
	
	private final StandartError errorBody;
	
	public UserAlreadyExistsException(String field, String value) {
		errorBody = new StandartError();
		errorBody.setTimestamp(LocalDateTime.now());
		errorBody.setStatus(HttpStatus.CONFLICT.value());
		errorBody.setError("There is already a user in an unique field!");
		errorBody.setMessage("This " + field + ": " + value + " is already in use");
		errorBody.setPath("test");
	}

	public StandartError getErrorBody() {
		return errorBody;
	}

}
