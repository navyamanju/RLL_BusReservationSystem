/**
 * 
 */
package com.busManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusDetailsExceptionHandler {

	@ExceptionHandler(value= {NullBusDetailsException.class})
	public ResponseEntity<ExceptionObject> handleNullBusDetailsException(NullBusDetailsException e){
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {BusDetailsAlreadyPresentException.class})
	public ResponseEntity<ExceptionObject> handleBusDetailsAlreadyPresentException(BusDetailsAlreadyPresentException e) {
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {BusDetailsNotFoundException.class})
	public ResponseEntity<ExceptionObject> handleBusDetailsNotFoundException(BusDetailsNotFoundException e) {
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
	}
	
}
