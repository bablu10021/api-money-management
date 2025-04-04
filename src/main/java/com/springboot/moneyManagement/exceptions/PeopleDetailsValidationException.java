package com.springboot.moneyManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.moneyManagement.response.ApiResponse;

@ControllerAdvice
public class PeopleDetailsValidationException{
	
	 @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
	        ApiResponse<String> response = new ApiResponse<>(500, "Internal Server Error: " + ex.getMessage(), null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }

}
