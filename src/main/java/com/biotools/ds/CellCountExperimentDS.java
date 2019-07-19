package com.biotools.ds;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.ConditionDTO;


@Service
public class CellCountExperimentDS {

	/**
	 * Sauvegarde en BDD l'expérience
	 * @param experiment
	 */
	//public void saveCellCountExperiment(ProliferationExperimentDTO experiment) {

	//}

	/**
	 * Déclanche le calcul de PD et DT
	 * @param conditionList
	 * @return
	 */
	public List<ConditionDTO> analyseCellCountExperiment(List<ConditionDTO> conditionList) {

		for (ConditionDTO condition : conditionList) {
			BigDecimal finalPD = new BigDecimal(0);
			if(condition.getInitialPopulationDoubling() != null) {	
				finalPD = condition.getInitialPopulationDoubling();
			}
			if (condition.getCellCountList() != null && !condition.getCellCountList().isEmpty()) {
				for (CellCountDTO count : condition.getCellCountList()) {
					count.setDoublingTime(this.doublingTimeComputation(count).setScale(2, RoundingMode.HALF_UP));
					count.setPopulationDoubling(this.populationDoublingComputation(count).setScale(2, RoundingMode.HALF_UP));
					// gestion du PD total en fonction du PD initial.
					finalPD = count.getPopulationDoubling().add(finalPD);
					count.setFinalPopulationDoubling(finalPD);
				
				}
			}
		}

		return conditionList;
	}

	 
	/**
	 * Calcul du doubling time
	 * @param cellCount
	 * @return
	 */
	private BigDecimal doublingTimeComputation(CellCountDTO cellCount) {

		LocalDateTime fromDateTime = LocalDateTime.ofInstant(cellCount.getBeginDay().toInstant(),
				ZoneId.systemDefault());
		LocalDateTime toDateTime = LocalDateTime.ofInstant(cellCount.getEndDay().toInstant(), ZoneId.systemDefault());
		double hours = LocalDateTime.from(fromDateTime).until(toDateTime, ChronoUnit.HOURS);
		double result = (Math.log10(2) * hours)
				/ (Math.log10(cellCount.getFinalQuantity()) - Math.log10(cellCount.getInitialQuantity()));

		return new BigDecimal(result);
	}

	/**
	 * Calcul du Population doubling
	 * @param cellCount
	 * @return
	 */
	private BigDecimal populationDoublingComputation(CellCountDTO cellCount) {
		double result = (Math.log10(cellCount.getFinalQuantity()) - Math.log10(cellCount.getInitialQuantity()))
				/ Math.log10(2);

		return new BigDecimal(result);
	}

}
