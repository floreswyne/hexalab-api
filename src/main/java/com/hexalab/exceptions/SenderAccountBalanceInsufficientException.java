package com.hexalab.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class SenderAccountBalanceInsufficientException extends RuntimeException {

	private static final long serialVersionUID = -6695051007774694343L;
	
	private final StandartError errorBody;
	
	public SenderAccountBalanceInsufficientException(String message) {
		super(message);
		errorBody = new StandartError();
		errorBody.setTimestamp(LocalDateTime.now());
		errorBody.setStatus(HttpStatus.CONFLICT.value());
		errorBody.setError("Insufficient balance for transfer!");
		errorBody.setMessage(message);
		errorBody.setPath("test");
	}

	public StandartError getErrorBody() {
		return errorBody;
	}

}
