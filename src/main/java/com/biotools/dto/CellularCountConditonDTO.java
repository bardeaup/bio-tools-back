package com.biotools.dto;

import lombok.Data;

@Data
public class CellularCountConditonDTO {
	private Long conditionId;
	private int replicatQuantity;

	/**
	 * Seeded cells for a particular conditionId (list of n CellCount with n =
	 * replicatQuantity + period treated)
	 */
	private CountingEventDTO seededCount;

	/**
	 * Final cell count for a particular conditionId (list of n CellCount with n =
	 * replicatQuantity + period treated)
	 */
	private CountingEventDTO finalCount;
}
