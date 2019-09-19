package com.biotools.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ConditionDTO {
	private Long id;
	private String cellLine;
	private boolean isAdherentCell;
	/**
	 * true if it's the first time this condition tested (first seeding event)
	 */
	private boolean firstSeeding;
	private int lastPeriod;
	private BigDecimal initialPopulationDoubling;
	private List<CellCountDTO> cellCountList;
	private List<TreatmentDTO> treatmentList;
}
