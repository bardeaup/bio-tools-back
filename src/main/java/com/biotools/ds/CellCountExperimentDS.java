package com.biotools.ds;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.ProliferationExperimentDTO;


@Service
public class CellCountExperimentDS {
	
	
	public void saveCellCountExperiment(String cellLine,
			CellCountDTO firstCount, CellCountDTO secondCount) {

	}

	public ProliferationExperimentDTO analyseCellCountExperiment(String cellLine,
			CellCountDTO firstCount, CellCountDTO secondCount) {
		ProliferationExperimentDTO experimentDTO = new ProliferationExperimentDTO();
		experimentDTO.setCellLine(cellLine);
		experimentDTO.setDoublingTime(this.doublingTimeComputation(cellLine, firstCount, secondCount).setScale(2,RoundingMode.HALF_UP));
		experimentDTO.setPopulationDoubling(this.populationDoublingComputation(cellLine, firstCount, secondCount).setScale(2,RoundingMode.HALF_UP));
		List<CellCountDTO> cellCountList = new ArrayList<>();
		cellCountList.add(firstCount);
		cellCountList.add(secondCount);
		experimentDTO.setCellCountList(cellCountList);
		return experimentDTO;
	}
	
	private BigDecimal doublingTimeComputation(String cellLine,
			CellCountDTO firstCount, CellCountDTO secondCount) {
		
		LocalDateTime fromDateTime = LocalDateTime.ofInstant(firstCount.getCountDate().toInstant(), ZoneId.systemDefault());
		LocalDateTime toDateTime = LocalDateTime.ofInstant(secondCount.getCountDate().toInstant(), ZoneId.systemDefault());
		double hours = LocalDateTime.from(fromDateTime).until(toDateTime, ChronoUnit.HOURS);
		double result = (Math.log10(2) * hours) / (Math.log10(secondCount.getCountValue()) - Math.log10(firstCount.getCountValue()));

		return new BigDecimal(result);
	}
	
	private BigDecimal populationDoublingComputation(String cellLine,
			CellCountDTO firstCount, CellCountDTO secondCount) {
		double result = (Math.log10(secondCount.getCountValue()) - Math.log10(firstCount.getCountValue())) / Math.log10(2);
		
		return new BigDecimal(result);
	}

}
