package com.exceptions;

/**
 * Represents a exception regarding command,
 * CRUD, or search processes
 * 
 * @author Daniel Echalar
 *
 */
public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {}

	public BadRequestException(Throwable cause) {
		super(cause);
	}
	
	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
