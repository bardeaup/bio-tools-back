package com.biotools.dto;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;

@Data
public class ConcentrationUnitDTO implements Serializable {

	private static final long serialVersionUID = -458366058286556743L;
	
	private String unitId;
	private String unitLabel;
	private BigInteger numericValue;

}
