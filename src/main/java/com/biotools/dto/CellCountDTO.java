package com.biotools.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CellCountDTO implements Serializable {

	private static final long serialVersionUID = 8235241511770606171L;
	private Date countDate;
	private int countValue;
}
