package com.hexalab.exceptions;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4957928830280378458L;

	public AccountNotFoundException(String message) {
		super(message);
	}

}
