package com.biotools.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.dto.ConditionDTO;
import com.biotools.dto.TreatmentDTO;
import com.biotools.entity.CellularCount;
import com.biotools.entity.Condition;
import com.biotools.entity.Experiment;
import com.biotools.entity.Treatment;
import com.biotools.repository.ConcentrationUnitRepository;

@Service
public class ExperimentMapper {
	
	@Autowired
	private ConcentrationUnitRepository concentrationUnitRepository;

	public Experiment cellularCountProjectDTOToProliferationExperimentEntity(
			CellularCountProjectDTO cellularCountProjectDTO) {
		Experiment proliferationExperiment = new Experiment();
		proliferationExperiment.setProjectName(cellularCountProjectDTO.getProjectName());
		// --- CONDITIONS --------
		List<Condition> conditions = new ArrayList<>();
		proliferationExperiment.getConditions();
		for (ConditionDTO conditionDTO : cellularCountProjectDTO.getConditionList()) {
			Condition condition = new Condition();
			condition.setExperiment(proliferationExperiment);
			condition.setCellLine(conditionDTO.getCellLine());
			condition.setInitialPopulationDoubling(conditionDTO.getInitialPopulationDoubling().doubleValue());
			// --- COMPTES CELLULAIRES --------
			if (conditionDTO.getCellCountList() != null && !conditionDTO.getCellCountList().isEmpty()) {
				List<CellularCount> cellCountList = new ArrayList<>();
				for (CellCountDTO cellCountDTO : conditionDTO.getCellCountList()) {
					CellularCount cellularCount = new CellularCount();
					cellularCount.setBeginDate(cellCountDTO.getBeginDate());
					cellularCount.setInitialQuantity(cellCountDTO.getInitialQuantity());
					cellularCount.setEndDate(cellCountDTO.getEndDate());
					cellularCount.setFinalQuantity(cellCountDTO.getFinalQuantity());
					cellularCount.setDt(cellCountDTO.getDoublingTime().doubleValue());
					cellularCount.setPd(cellCountDTO.getPopulationDoubling().doubleValue());
					cellularCount.setFinalPd(cellCountDTO.getFinalPopulationDoubling().doubleValue());
					cellularCount.setCondition(condition);
					cellCountList.add(cellularCount);
				}
				condition.getCellularCountList().addAll(cellCountList);
			}
			List<Treatment> treatmentList = new ArrayList<>();
			if(conditionDTO.getTreatmentList() != null && !conditionDTO.getTreatmentList().isEmpty()) {
				for(TreatmentDTO treatmentDTO : conditionDTO.getTreatmentList()) {
					Treatment treatment = new Treatment();
					treatment.setUnit(concentrationUnitRepository.findById(treatmentDTO.getConcentrationUnitId()));
					treatment.setName(treatmentDTO.getName());
					treatment.setConcentrationValue(treatmentDTO.getConcentrationValue());
					treatment.setCondition(condition);
					treatmentList.add(treatment);
				}
			} 
			condition.setTreatmentList(treatmentList);
			conditions.add(condition);
		}
		proliferationExperiment.getConditions().addAll(conditions);

		return proliferationExperiment;
	};


}
