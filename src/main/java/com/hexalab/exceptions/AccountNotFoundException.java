package com.hexalab.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4957928830280378458L;
	
	private final StandartError errorBody;
	
	public AccountNotFoundException(String accountId) {
		errorBody = new StandartError();
		errorBody.setTimestamp(LocalDateTime.now());
		errorBody.setStatus(HttpStatus.NOT_FOUND.value());
		errorBody.setError("Account not found!");
		errorBody.setMessage("Account with ID: " + accountId + " cannot be found!");
		errorBody.setPath("test");
	}

	public StandartError getErrorBody() {
		return errorBody;
	}

}
