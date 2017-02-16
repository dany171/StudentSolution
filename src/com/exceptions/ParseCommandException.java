package com.exceptions;

public class ParseCommandException extends Exception{
	
	public ParseCommandException(){}
	
	public ParseCommandException(Throwable cause)
	{
		super(cause);
	}
	
	public ParseCommandException(String message, Throwable cause)
	{
		super(message, cause);
	}	
}
