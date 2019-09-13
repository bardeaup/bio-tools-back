package com.biotools.dto;

import java.util.List;

import lombok.Data;

@Data
public class CountingEventDTO {
	private int periodId;
	private List<CellCountDTO> countList;
}
