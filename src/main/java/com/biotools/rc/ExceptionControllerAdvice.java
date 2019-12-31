package com.biotools.rc;

import com.biotools.exceptions.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.biotools.exceptions.UnicityConstraintException;
import com.biotools.model.CustomError;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { UnicityConstraintException.class})
	protected ResponseEntity<CustomError> handleUniqueConstraintViolation(UnicityConstraintException e){
		return new ResponseEntity<>(new CustomError(e.getReason() + " already used.", "WARNING"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { DataNotFoundException.class})
	protected ResponseEntity<CustomError> handleDataNotFoundException(DataNotFoundException e){
		return new ResponseEntity<>(new CustomError(e.getMessage(), "INFO"), HttpStatus.BAD_REQUEST);
	}
}
