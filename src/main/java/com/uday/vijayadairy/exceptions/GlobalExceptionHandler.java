package com.uday.vijayadairy.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity <Map<String, Object>> exceptionhandler(MethodArgumentNotValidException ex)
	{
		Map<String, Object> body=new LinkedHashMap<String, Object>();
		Map<String, String> errors=new LinkedHashMap<String, String>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
		errors.put(error.getField(), error.getDefaultMessage()));
		body.put("errors", errors);
		body.put("productStatus", HttpStatus.BAD_REQUEST.value());	
		return new ResponseEntity<Map<String,Object>>(body, HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String,Object>> handleAllExceptions(Exception ex) {
		 System.out.println(ex.getMessage());
	        Map<String,Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDateTime.now());
	        body.put("productStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
	        body.put("message", ex.getMessage());
	        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 
	 
}
