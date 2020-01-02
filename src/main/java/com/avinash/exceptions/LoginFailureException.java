package com.avinash.exceptions;

public class LoginFailureException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public LoginFailureException(String message) {
		super(message);
	}
}
