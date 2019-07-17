package com.biotools.exceptions;

public class UsernameAlreadyUsedException extends RuntimeException {

	private static final long serialVersionUID = -5480576874810622950L;

	public UsernameAlreadyUsedException(String errorMessage) {
		super(errorMessage);
	}
}