package com.biotools.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CountTreatmentDTO {
	private int replicatId;
	private Date initialDate;
	private Date finalDate;
	private int initialQuantity;
	private int finalQuantity;
	private BigDecimal initialPopulationDoubling;
	private BigDecimal finalPopulationDoubling;
	private BigDecimal doublingTime;
}
