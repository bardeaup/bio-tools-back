package com.biotools.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ConditionDTO {
	private String cellLine;
	private boolean isAdherentCell;
	private BigDecimal initialPopulationDoubling;
	private List<CellCountDTO> cellCountList;
	private List<TreatmentDTO> treatmentList;
}
