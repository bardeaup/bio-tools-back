package com.biotools.dto;

import java.util.List;

import lombok.Data;

@Data
public class CellularCountProjectDTO {
	private String projectName;
	private List<ConditionDTO> conditionList; 
}
