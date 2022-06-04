package com.hexalab.exceptions;

public class TransactionNotRealizedException extends RuntimeException {

	private static final long serialVersionUID = -3294129482856726467L;

	public TransactionNotRealizedException(String message) {
		super(message);
	}

}
