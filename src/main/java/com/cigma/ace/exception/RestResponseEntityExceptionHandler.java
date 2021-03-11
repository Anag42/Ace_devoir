package com.cigma.ace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.cigma.ace.model.ErrorMessage;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleAccessDeniedException(
		      RuntimeException ex, WebRequest request) {

	    ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
	    
		return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleOtherExceptions(
	    RuntimeException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
		
	    return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}