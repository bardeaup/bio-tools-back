package com.biotools.as;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biotools.ds.CellCountExperimentDS;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.entity.Experiment;
import com.biotools.mapper.ExperimentMapperMapstruct;

@Service
public class CellCountExperimentAS {

	@Autowired
	CellCountExperimentDS cellCountExperimentDS;

	@Autowired
	ExperimentMapperMapstruct mapper;

	/**
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public CellularCountProjectDTO saveAndAnalyseExperiement(CellularCountProjectDTO p) throws Exception {

		boolean projectNameError = this.cellCountExperimentDS.isExperimentNameAlreadyUsed(p.getProjectName());
		if(projectNameError) {
			throw new Exception("Project name already used");
		}
		if (p != null && p.getConditionList() != null && !p.getConditionList().isEmpty()) {
			p.setConditionList(this.cellCountExperimentDS.analyseCellCountExperiment(p.getConditionList()));
			this.cellCountExperimentDS.saveCellCountExperiment(p);
			return p;
		} else {
			throw new Exception("UNCOMPLETE_DATA_SET");
		}

	}

	/**
	 * Chargement en BDD des expériences de l'utilisateur
	 * @return liste d'expérience de prolifération
	 */
	public List<CellularCountProjectDTO> loadExistingUserExperiment() {
		List<CellularCountProjectDTO> cellularCountProjectDTOs = null;
		List<Experiment> experimentListEntity = cellCountExperimentDS.loadUserExistingExperiment();
		if (experimentListEntity != null && !experimentListEntity.isEmpty()) {
			cellularCountProjectDTOs = this.mapper.proliferationExperimentEntityListToDto(experimentListEntity);
		}
		return cellularCountProjectDTOs;
	}
}
