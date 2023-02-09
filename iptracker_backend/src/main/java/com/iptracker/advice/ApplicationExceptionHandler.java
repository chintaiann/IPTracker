package com.iptracker.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.iptracker.exception.IPNotFoundException;
import com.iptracker.exception.InvalidIPException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidIPException.class)
	public Map<String,String> handleInvalidIP(InvalidIPException ex) { 
		Map<String,String> errorMap = new HashMap<>();
		errorMap.put("errorMessage" , ex.getMessage()); 
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IPNotFoundException.class) 
	public Map<String,String> handleIPNotFound(IPNotFoundException ex) { 
		Map<String,String> errorMap = new HashMap<>();
		errorMap.put("errorMessage" , ex.getMessage()); 
		return errorMap;
	}
	
}
