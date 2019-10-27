package com.biotools.as;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.CellularCountConditonDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.dto.CountForAnalysisDTO;
import com.biotools.entity.CellularCount;
import com.biotools.entity.Condition;
import com.biotools.entity.Experiment;
import com.biotools.exceptions.UnicityConstraintException;
import com.biotools.mapper.ExperimentMapper;
import com.biotools.mapper.ExperimentMapperMapstruct;
import com.biotools.repository.ProliferationExperimentRepository;
import com.biotools.service.CellCountExperimentDS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CellCountExperimentAS {

	private static final int FIRST_PERIOD = 1;
	private CellCountExperimentDS cellCountExperimentDS;
	private ExperimentMapper experimentMapper;
	private ExperimentMapperMapstruct mapper;

	// autowire by constructor
	public CellCountExperimentAS(CellCountExperimentDS cellCountExperimentDS, ExperimentMapperMapstruct mapper,
			ExperimentMapper experimentMapper, ProliferationExperimentRepository experimentRepo) {
		this.cellCountExperimentDS = cellCountExperimentDS;
		this.mapper = mapper;
		this.experimentMapper = experimentMapper;
	}

	/**
	 * Experiment saving when user design it the first time
	 * @param p experiment designed by user
	 * @return experiment id fromm database
	 * @throws UnicityConstraintException if prject name already used
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
	 * Triggers PD and DT evaluation and save all cellular count related data in DB 
	 * @param cellularCountConditonDTO from IHM 
	 * @return cellularCountConditonDTO updated with data calculated and saved in DB
	 */
	@Transactional
	public CellularCountConditonDTO saveCellCount(CellularCountConditonDTO cellularCountConditonDTO) {

		// Chargement de la condition en cours de traitement
		Condition condition = this.cellCountExperimentDS.findConditionById(cellularCountConditonDTO.getConditionId());
		
		// Première période pouvant être enregistrée, dans ce cas là, la liste du compte final 
		// est null, seul les cellules ensemmencées seront sauvegardées.
		int actualPeriod = 1;
		
		// Récupération de la quantité ensemmencée
		if (cellularCountConditonDTO.getFinalCounts() != null) {
			actualPeriod = cellularCountConditonDTO.getFinalCounts().get(0).getPeriod();
			// Déclancher le calcul PD et DT, retourner la liste finale avec les PD et DT
			// calculés et
			List<CellularCount> cellularCountsSeeded = this.cellCountExperimentDS
					.findCellularCountListByConditionIdAndPeriod(cellularCountConditonDTO.getConditionId(),
							actualPeriod);
			// Mapping de la liste de CountTreatmentDTO
			List<CountForAnalysisDTO> countListForAnalysis = new ArrayList<>();
			for (CellularCount seededCount : cellularCountsSeeded) {
				CountForAnalysisDTO countForAnalysis = new CountForAnalysisDTO();
				countForAnalysis.setReplicatId(seededCount.getReplicatId());
				countForAnalysis.setInitialDate(seededCount.getDate());
				countForAnalysis.setInitialQuantity(seededCount.getQuantity());
				countForAnalysis.setInitialPopulationDoubling(new BigDecimal(seededCount.getPd()));
				for (CellCountDTO finalCount : cellularCountConditonDTO.getFinalCounts()) {
					if (finalCount.getReplicatId() == seededCount.getReplicatId()) {
						countForAnalysis.setFinalDate(finalCount.getDate());
						countForAnalysis.setFinalQuantity(finalCount.getQuantity());
					}
				}
				countListForAnalysis.add(countForAnalysis);
			}
			List<CountForAnalysisDTO> analysedCountList = this.cellCountExperimentDS
					.analyseCellCountExperiment(countListForAnalysis);

			// Mapping List<CountForAnalysisDTO> to List<CellularCount>
			List<CellularCount> finalCountList = new ArrayList<>();
			for (CountForAnalysisDTO analysedCount : analysedCountList) {
				CellularCount cellularCountEntity = new CellularCount();
				cellularCountEntity.setDate(analysedCount.getFinalDate());
				cellularCountEntity.setDt(analysedCount.getDoublingTime().doubleValue());
				cellularCountEntity.setPd(analysedCount.getFinalPopulationDoubling().doubleValue());
				cellularCountEntity.setPeriod(actualPeriod);
				cellularCountEntity.setReplicatId(analysedCount.getReplicatId());
				cellularCountEntity.setCondition(condition);
				cellularCountEntity.setQuantity(analysedCount.getFinalQuantity());
				finalCountList.add(cellularCountEntity);
				
				// Mise à jour du PD de chaque réplicat ensemmencé pour la prochaine période
				for (CellCountDTO seededCount : cellularCountConditonDTO.getSeededCounts()) {
					if (seededCount.getReplicatId() == analysedCount.getReplicatId()) {
						seededCount.setPopulationDoubling(analysedCount.getFinalPopulationDoubling());
					}
				}
			}
			condition.addCellularCountList(finalCountList);
		}

		// Mapping et enregistrement de la liste de compte de cellules ensemencées -
		// récupération de la condition pour permettre la liaison.
		List<CellularCount> seededCountList = mapper.cellCountDTOListToEntity(cellularCountConditonDTO.getSeededCounts());
		for (CellularCount count : seededCountList) {
			count.setCondition(condition);
		}
		condition.addCellularCountList(seededCountList);
		
		// Si il y a une quantité de cellules ensemencées pour la prochaine période étudiée,
		// actualPeriod représentant la période actuellement en cours de traitement 
		// pour une condition donnée prend cette dernière valeur.
		if(cellularCountConditonDTO.getSeededCounts() != null 
				&& !cellularCountConditonDTO.getSeededCounts().isEmpty()
				&& cellularCountConditonDTO.getSeededCounts().get(0).getPeriod() > 0) {
			actualPeriod = cellularCountConditonDTO.getSeededCounts().get(0).getPeriod();
		}
		condition.setActualPeriod(actualPeriod);
		
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
	 * When a count event has already been done, a boolean alreadyStarted = true
	 * (mapping)
	 * 
	 * @param name
	 * @return CellularCountProjectDTO
	 */
	public CellularCountProjectDTO loadExistingUserExperimentByName(String name) {
		CellularCountProjectDTO cellularCountProjectDTO = null;
		Experiment experiment = cellCountExperimentDS.loadUserExistingExperimentByName(name);
		return this.mapper.proliferationExperimentEntityToDto(experiment);
	}

	public CellularCountProjectDTO loadExistingUserExperimentById(Long id) {
		CellularCountProjectDTO cellularCountProjectDTO = null;
		Experiment experiment = cellCountExperimentDS.loadUserExistingExperimentById(id);
		return this.mapper.proliferationExperimentEntityToDto(experiment);
	}
}
