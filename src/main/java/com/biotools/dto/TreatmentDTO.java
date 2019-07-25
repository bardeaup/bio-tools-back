package com.biotools.dto;

import lombok.Data;

@Data
public class TreatmentDTO {
	private String name;
	private int concentrationValue;
	private Long concentrationUnitId;
	private String concentrationUnit;
}
