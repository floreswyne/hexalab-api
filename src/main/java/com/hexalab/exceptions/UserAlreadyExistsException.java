package com.hexalab.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -7186443880620020979L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
