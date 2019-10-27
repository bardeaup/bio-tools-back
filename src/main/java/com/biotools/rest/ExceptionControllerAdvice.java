package com.biotools.rest;

import com.biotools.exceptions.UnicityConstraintException;
import com.biotools.model.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { UnicityConstraintException.class})
	protected ResponseEntity<CustomError> handleUniqueConstraintViolation(UnicityConstraintException e){
		return new ResponseEntity<CustomError>(new CustomError(e.getReason() + " already used.", "WARNING"), HttpStatus.BAD_REQUEST);
	}

}
