package com.hexalab.exceptions;

public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6928009107906396723L;

	public TransactionNotFoundException(String message) {
		super(message);
	}

}
