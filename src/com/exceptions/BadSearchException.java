package com.exceptions;

public class BadSearchException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadSearchException() {

	}

	public BadSearchException(String message) {
		super(message);
	}

	public BadSearchException(Throwable cause) {
		super(cause);
	}

	public BadSearchException(String message, Throwable cause) {
		super(message, cause);
	}
}
