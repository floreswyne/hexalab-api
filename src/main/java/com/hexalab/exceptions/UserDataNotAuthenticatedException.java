package com.hexalab.exceptions;

public class UserDataNotAuthenticatedException extends RuntimeException {

	private static final long serialVersionUID = 4777968674201517267L;

	public UserDataNotAuthenticatedException(String message) {
		super(message);
	}

}
