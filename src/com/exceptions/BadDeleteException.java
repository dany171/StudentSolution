package com.exceptions;

public class BadDeleteException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadDeleteException() {
	}

	public BadDeleteException(Throwable cause) {
		super(cause);
	}

	public BadDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

}
