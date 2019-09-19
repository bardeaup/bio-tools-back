package com.biotools.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CellCountDTO implements Serializable {

	private static final long serialVersionUID = 8235241511770606171L;
	private Long replicatId;
	private Date date;
	private int period;
	private int quantity;
	private BigDecimal populationDoubling;
	private BigDecimal doublingTime;
}
