package com.hexalab.exceptions;

public class TransferNotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -2954102858039767585L;

	public TransferNotAuthorizedException(String message) {
		super(message);
	}

}
