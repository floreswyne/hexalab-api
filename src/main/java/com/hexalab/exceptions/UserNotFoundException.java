package com.hexalab.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5405807362027288212L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
