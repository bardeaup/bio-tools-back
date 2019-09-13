package com.biotools.ds;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.dto.ConditionDTO;
import com.biotools.entity.CellularCount;
import com.biotools.entity.Experiment;
import com.biotools.mapper.ExperimentMapper;
import com.biotools.repository.CellularCountRepository;
import com.biotools.repository.ProliferationExperimentRepository;
import com.biotools.repository.UserRepository;
import com.biotools.security.services.UserPrinciple;

@Service
public class CellCountExperimentDS {

	@Autowired
	private ExperimentMapper experimentMapper;

	@Autowired
	private ProliferationExperimentRepository experimentRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private CellularCountRepository cellularCountRepo;
	
	@Transactional
	public boolean isExperimentNameAlreadyUsed(String projectName) {
		UserPrinciple principal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return experimentRepo.existsByUserIdAndProjectName(principal.getId(), projectName);
	}
	
	@Transactional
	public List<CellularCount> findCellularCountListByConditionId(Long conditionId){
		return this.cellularCountRepo.findByConditionId(conditionId);
	}
	
	@Transactional
	public List<CellularCount> saveCellularCountList(List<CellularCount> cellularCountList){
		return this.cellularCountRepo.saveAll(cellularCountList);
	}
	

	/**
	 * Sauvegarde en BDD l'expérience
	 * @param project
	 * @return experiment id
	 */
	@Transactional
	public Experiment saveCellCountExperiment(CellularCountProjectDTO project) {

		// Récupération de l'id User
		UserPrinciple principal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Mapping vers Entity
		Experiment proliferationExperimentEntity = experimentMapper
				.cellularCountProjectDTOToProliferationExperimentEntity(project);

		proliferationExperimentEntity.setUser(userRepo.findUserById(principal.getId()));
		Experiment savedExp = experimentRepo.saveAndFlush(proliferationExperimentEntity);
		return savedExp;
	}
	
	public void analyseCellCount(List<CellCountDTO> actualCellCountList, List<CellCountDTO> previousCellCountList){
		
		
	}

	/**
	 * Déclanche le calcul de PD et DT
	 * 
	 * @param conditionList
	 * @return
	 */
	public List<ConditionDTO> analyseCellCountExperiment(List<ConditionDTO> conditionList) {

		for (ConditionDTO condition : conditionList) {
			BigDecimal finalPD = new BigDecimal(0);
			if (condition.getInitialPopulationDoubling() != null) {
				finalPD = condition.getInitialPopulationDoubling();
			}
			if (condition.getCellCountList() != null && !condition.getCellCountList().isEmpty()) {
				for (CellCountDTO count : condition.getCellCountList()) {
//					count.setDoublingTime(this.doublingTimeComputation(count).setScale(2, RoundingMode.HALF_UP));
//					count.setPopulationDoubling(
//							this.populationDoublingComputation(count).setScale(2, RoundingMode.HALF_UP));
//					// gestion du PD total en fonction du PD initial.
//					finalPD = count.getPopulationDoubling().add(finalPD);
//					count.setFinalPopulationDoubling(finalPD);

				}
			}
		}

		return conditionList;
	}

	@Transactional
	public List<Experiment> loadUserExistingExperiment() {
		// Récupération de l'id User
		UserPrinciple principal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Experiment> experimentList = this.experimentRepo.findAllByUserId(principal.getId()); 
		return experimentList;
	}
	
	@Transactional
	public Experiment loadUserExistingExperimentByName(String name) {
		UserPrinciple principal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Experiment experiment = this.experimentRepo.findByUserIdAndProjectNameIgnoreCase(principal.getId(), name);
		return experiment;
	}
	
	@Transactional
	public Experiment loadUserExistingExperimentById(Long id) {
		UserPrinciple principal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Experiment experiment = this.experimentRepo.findByUserIdAndId(principal.getId(), id);
		return experiment;
	}

	/**
	 * Calcul du doubling time
	 * 
	 * @param cellCount
	 * @return
	 */
//	private BigDecimal doublingTimeComputation(CellCountDTO cellCount) {
//
//		LocalDateTime fromDateTime = LocalDateTime.ofInstant(cellCount.getBeginDate().toInstant(),
//				ZoneId.systemDefault());
//		LocalDateTime toDateTime = LocalDateTime.ofInstant(cellCount.getEndDate().toInstant(), ZoneId.systemDefault());
//		double hours = LocalDateTime.from(fromDateTime).until(toDateTime, ChronoUnit.HOURS);
//		double result = (Math.log10(2) * hours)
//				/ (Math.log10(cellCount.getFinalQuantity()) - Math.log10(cellCount.getInitialQuantity()));
//
//		return new BigDecimal(result);
//	}

	/**
	 * Calcul du Population doubling
	 * 
	 * @param cellCount
	 * @return
	 */
//	private BigDecimal populationDoublingComputation(CellCountDTO cellCount) {
//		double result = (Math.log10(cellCount.getFinalQuantity()) - Math.log10(cellCount.getInitialQuantity()))
//				/ Math.log10(2);
//
//		return new BigDecimal(result);
//	}

}
