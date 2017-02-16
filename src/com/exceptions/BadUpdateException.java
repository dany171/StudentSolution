package com.exceptions;

public class BadUpdateException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public BadUpdateException(){}

	public BadUpdateException(Throwable cause)
	{
		super(cause);
	}
	
	public BadUpdateException(String message, Throwable cause)
	{
		super(message, cause);
	}	
}
