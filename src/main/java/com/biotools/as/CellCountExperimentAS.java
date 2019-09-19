package com.biotools.as;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biotools.ds.CellCountExperimentDS;
import com.biotools.dto.CellCountDTO;
import com.biotools.dto.CellularCountConditonDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.dto.ConditionDTO;
import com.biotools.entity.CellularCount;
import com.biotools.entity.Condition;
import com.biotools.entity.Experiment;
import com.biotools.exceptions.UnicityConstraintException;
import com.biotools.mapper.ExperimentMapper;
import com.biotools.mapper.ExperimentMapperMapstruct;
import com.biotools.repository.ProliferationExperimentRepository;

@Service
public class CellCountExperimentAS {

	private static final int FIRST_PERIOD = 1;
	private CellCountExperimentDS cellCountExperimentDS;
	private ExperimentMapper experimentMapper;
	private ExperimentMapperMapstruct mapper;
	
	//autowire by constructor
	public CellCountExperimentAS(CellCountExperimentDS cellCountExperimentDS, 
			ExperimentMapperMapstruct mapper, ExperimentMapper experimentMapper,
			ProliferationExperimentRepository experimentRepo) {
		this.cellCountExperimentDS = cellCountExperimentDS;
		this.mapper = mapper;
		this.experimentMapper = experimentMapper;
	}

	/**
	 * 
	 * @param p
	 * @return experiment id form database
	 * @throws UnicityConstraintException
	 */
	@Transactional
	public CellularCountProjectDTO saveProliferationExperiment(CellularCountProjectDTO p)
			throws UnicityConstraintException {

		boolean projectNameError = this.cellCountExperimentDS.isExperimentNameAlreadyUsed(p.getProjectName());
		if (projectNameError) {
			throw new UnicityConstraintException("project name");
		}
		return this.mapper.proliferationExperimentEntityToDto(this.cellCountExperimentDS.saveCellCountExperiment(p));
	}

	/**
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public CellularCountProjectDTO saveAndAnalyseExperiement(CellularCountProjectDTO p) throws Exception {

		boolean projectNameError = this.cellCountExperimentDS.isExperimentNameAlreadyUsed(p.getProjectName());
		if (projectNameError) {
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

	public CellularCountConditonDTO saveCellCount(CellularCountConditonDTO cellularCountConditonDTO) {

		// Recherche des comptes cellulaires existants pour cette condition
//		List<CellularCount> cellularCounts = this.cellCountExperimentDS
//				.findCellularCountListByConditionId(cellularCountConditonDTO.getConditionId());

		
		// Récupération de la quantité ensemmencée
		

		// Si il en existe -> calcul PD et DT
		List<CellularCount> seededCountList = mapper.cellCountDTOListToEntity(cellularCountConditonDTO.getSeededCounts());
		
		Condition condition = this.cellCountExperimentDS.findConditionById(cellularCountConditonDTO.getConditionId());
		for(CellularCount count : seededCountList) {
			count.setCondition(condition);
		}
		condition.addCellularCountList(seededCountList);		
		condition = this.cellCountExperimentDS.saveCondition(condition);
		
		

		return cellularCountConditonDTO;
	}

	/**
	 * Chargement en BDD des expériences de l'utilisateur
	 * 
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

	/**
	 * Return Experiment , Detail, Conditions with Treatments but without CountList
	 * When a count event has already been done, a boolean alreadyStarted = true (mapping)  
	 * @param name
	 * @return CellularCountProjectDTO
	 */
	public CellularCountProjectDTO loadExistingUserExperimentByName(String name) {
		CellularCountProjectDTO cellularCountProjectDTO = null;
		Experiment experiment = cellCountExperimentDS.loadUserExistingExperimentByName(name);
		cellularCountProjectDTO = this.mapper.proliferationExperimentEntityToDto(experiment);
		for(ConditionDTO conditionDTO: cellularCountProjectDTO.getConditionList()) {
			int period = 0;
			for(CellCountDTO cellCount : conditionDTO.getCellCountList()) {
				while(cellCount.getPeriod() > period) {
					period = cellCount.getPeriod();
				}
			}
			conditionDTO.setLastPeriod(period);
		}
		return cellularCountProjectDTO;
	}

	public CellularCountProjectDTO loadExistingUserExperimentById(Long id) {
		CellularCountProjectDTO cellularCountProjectDTO = null;
		Experiment experiment = cellCountExperimentDS.loadUserExistingExperimentById(id);
		
		
		
		return this.mapper.proliferationExperimentEntityToDto(experiment);
	}
}
