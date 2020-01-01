package com.avinash.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(ApplicationException.class)
	public String handleException() {
		System.out.println("Inside global handleException in ApplicationExceptionHandler");
		return "error";
	}
	
	

}
