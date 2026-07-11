package com.taskManagementSystem.project.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionalHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				exception.getMessage(),
				webRequest.getDescription(false)
				);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception){
		
		Map<String, String>errors=new HashMap<>();
		exception.getBindingResult()
				 .getFieldErrors()
				 .forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDetails> handleTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest webRequest) {
		String errorDescription = String.format(
				"The parameter '%s' with value '%s' could not be converted to type '%s'",
				exception.getName(), exception.getValue(), 
				exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "Unknown"
		);

		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				"Invalid ID Format provided in the URL path.",
				errorDescription
		);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				"An unexpected eror occurred:"+exception.getMessage(),
				webRequest.getDescription(false)
				);
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
