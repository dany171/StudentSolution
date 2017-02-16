package com.exceptions;

public class BadStudentException extends Exception{

	private static final long serialVersionUID = 1L;

	public BadStudentException(){
		
	}
	
	public BadStudentException(String message)
	{
		super(message);
	}

	public BadStudentException(Throwable cause)
	{
		super(cause);
	}

	public BadStudentException(String message, Throwable cause)
	{
		super(message, cause);
	}	
}
