package com.biotools.dto;

import java.util.List;
import lombok.Data;

@Data
public class ConditionDTO {
	private String cellLine;
	private int initialPopulationDoubling;
	private List<CellCountDTO> cellCountList;
	private List<TreatmentDTO> treatmentList;
}
