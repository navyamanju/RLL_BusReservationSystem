
package com.busManagement.exception;

public class NullBusDetailsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public NullBusDetailsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NullBusDetailsException(String message) {
		super(message);
	}

}
