package com.cushina.common.exception;

public class EmailNotUniqueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailNotUniqueException(String message)
	{
		super(message);
	}
}
