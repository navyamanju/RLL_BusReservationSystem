package com.busManagement.exception;

public class NullUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullUserException(String message, Throwable throwable) {
		super(message,throwable);
		// TODO Auto-generated constructor stub
	}

	public NullUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
