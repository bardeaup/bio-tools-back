package com.biotools.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnicityConstraintException extends RuntimeException {

	private static final long serialVersionUID = -4884496521622233915L;
	
	private String reason;
	
}
