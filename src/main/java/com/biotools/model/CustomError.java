package com.biotools.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomError implements Serializable{

	private static final long serialVersionUID = 1L;
	private String msg;
	private String severity;
}
