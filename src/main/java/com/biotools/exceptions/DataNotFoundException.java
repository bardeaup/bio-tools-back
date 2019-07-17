package com.biotools.exceptions;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5480576874810622950L;

	public DataNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}