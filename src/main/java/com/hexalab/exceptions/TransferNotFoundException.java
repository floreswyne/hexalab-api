package com.hexalab.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class TransferNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6928009107906396723L;
	
	private final StandartError errorBody;
	
	public TransferNotFoundException(String transferId) {
		errorBody = new StandartError();
		errorBody.setTimestamp(LocalDateTime.now());
		errorBody.setStatus(HttpStatus.NOT_FOUND.value());
		errorBody.setError("Transfer not found!");
		errorBody.setMessage("Transfer with ID: " + transferId + " cannot be found!");
		errorBody.setPath("test");
	}

	public StandartError getErrorBody() {
		return errorBody;
	}
	
}
