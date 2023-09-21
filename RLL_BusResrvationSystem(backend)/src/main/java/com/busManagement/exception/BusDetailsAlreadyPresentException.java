/**
 * 
 */
package com.busManagement.exception;

public class BusDetailsAlreadyPresentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public BusDetailsAlreadyPresentException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusDetailsAlreadyPresentException(String message) {
		super(message);
	}

}
