package com.biotools.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProliferationExperimentDTO implements Serializable{

	private static final long serialVersionUID = 2885178033870685081L;
	private String cellLine;
	private List<CellCountDTO> cellCountList;
	private BigDecimal populationDoubling;
	private BigDecimal doublingTime;
}
