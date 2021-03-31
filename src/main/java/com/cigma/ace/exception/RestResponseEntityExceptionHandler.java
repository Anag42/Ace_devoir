package com.cigma.ace.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.auth0.jwt.exceptions.TokenExpiredException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler{
	
	@ExceptionHandler(ModelNotFoundException.class)
	protected ResponseEntity<Object> handleModelNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exResp = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exResp, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {InvalidPasswordResetTokenException.class, DeleteProductStillInCartException.class})
	protected ResponseEntity<Object> handleInvalidPasswordResetTokenException(Exception ex, WebRequest request) {
		ExceptionResponse exResp = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exResp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<Object> handleAuthenticationException(Exception ex, WebRequest request) {
		ExceptionResponse exResp = new ExceptionResponse(new Date(), "Invalid Username/Password!",
				request.getDescription(false));

		return new ResponseEntity<>(exResp, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	protected ResponseEntity<Object> handleTokenExpiredException(Exception ex, WebRequest request) {
		ExceptionResponse exResp = new ExceptionResponse(new Date(), "Token Expired!",
				request.getDescription(false));

		return new ResponseEntity<>(exResp, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UnsupportedOperationException.class)
	protected ResponseEntity<Object> handleModelUnsupportedOperationException(Exception ex, WebRequest request) {
		ExceptionResponse exResp = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exResp, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleBadRequestException(MethodArgumentNotValidException ex) {
		List<BadRequestError> errorMessages  = ex.getBindingResult().getFieldErrors().stream()
	            .map(err -> new BadRequestError(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
	            .distinct()
	            .collect(Collectors.toList());
		BadRequestResponse exResp = BadRequestResponse.builder().errorMessage(errorMessages).build();
		
		return new ResponseEntity<>(exResp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exResp = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}