
package com.busManagement.exception;

public class BusDetailsNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public BusDetailsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusDetailsNotFoundException(String s) {
		super(s);
	}

}
