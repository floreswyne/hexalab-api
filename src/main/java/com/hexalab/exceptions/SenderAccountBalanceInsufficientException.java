package com.hexalab.exceptions;

public class SenderAccountBalanceInsufficientException extends RuntimeException {

	private static final long serialVersionUID = -6695051007774694343L;

	public SenderAccountBalanceInsufficientException(String message) {
		super(message);
	}

}
