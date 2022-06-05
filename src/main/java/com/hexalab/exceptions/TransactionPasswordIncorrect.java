package com.hexalab.exceptions;

public class TransactionPasswordIncorrect extends RuntimeException {

	private static final long serialVersionUID = -1864438456464842622L;
	
	public TransactionPasswordIncorrect(String message) {
		super(message);
	}

}
