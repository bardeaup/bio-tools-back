package com.biotools.dto;

import java.util.List;

import lombok.Data;

@Data
public class  CellularCountProjectDTO {
	private String projectName;
	private DetailDTO detail;
	private List<ConditionDTO> conditionList; 
}
