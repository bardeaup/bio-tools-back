package com.biotools.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ErrorDTO implements Serializable {

	private static final long serialVersionUID = -218519264836354272L;
	
	private String severity;
	private String message;
}
