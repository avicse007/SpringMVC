package com.avinash.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler({ApplicationException.class,AsyncRequestTimeoutException.class})
	public String handleException() {
		System.out.println("Inside global handleException in ApplicationExceptionHandler");
		return "error";
	}
	
	

}
