package com.biotools.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.dto.ConditionDTO;
import com.biotools.dto.DetailDTO;
import com.biotools.dto.TreatmentDTO;
import com.biotools.entity.CellularCount;
import com.biotools.entity.Condition;
import com.biotools.entity.Detail;
import com.biotools.entity.Experiment;
import com.biotools.entity.Treatment;

@Mapper(componentModel = "spring")
public interface ExperimentMapperMapstruct {
	
	@Mapping(target="conditions", source="conditionList")
	Experiment cellularCountProjectDTOToProliferationExperimentEntity(
			CellularCountProjectDTO cellularCountProjectDTO);
		
	List<Condition> ConditionDTOListToEntity (List<ConditionDTO> conditionDTOList);
	
	@Mapping(target="cellularCountList", source="cellCountList")
	Condition ConditionDTOToEntity(ConditionDTO conditionDTO);

	List<CellularCount> cellCountDTOListToEntity (List<CellCountDTO> cellCountDTOList);
	@Mappings({
		@Mapping(target="condition.id", source="conditionId"),
		@Mapping(target="pd", source="populationDoubling"),
		@Mapping(target="dt", source="doublingTime")
	})
	CellularCount cellCountDTOToEntity (CellCountDTO cellCountDTO);
	
	List<Treatment> treatmentDTOListToEntity (List<TreatmentDTO> treatmentDTOList);
	
	Treatment treatmentDTOToEntity (TreatmentDTO treatmentDTO);

	// --------- Mapping inverse --------------------
	

	List<CellularCountProjectDTO> proliferationExperimentEntityListToDto(
			List<Experiment> experimentList);
	
	@Mapping(target="conditionList", source="conditions")
	CellularCountProjectDTO proliferationExperimentEntityToDto(
			Experiment experiment);
	
	DetailDTO detailEntityToDto(Detail detail);
		
	List<ConditionDTO> ConditionEntityListToDto (List<Condition> conditionList);
	
	@Mapping(target="cellCountList", source="cellularCountList")
	ConditionDTO conditionEntityToDto(Condition condition);
	
	List<CellCountDTO> cellCountEntityListToDto (List<CellularCount> cellCountEntityList);
	
	@Mappings({
	      @Mapping(target="populationDoubling", source="pd"),
	      @Mapping(target="doublingTime", source="dt")
	    })
	CellCountDTO cellCountEntityToDto (CellularCount cellCountEntity);
	
	List<TreatmentDTO> treatmentEntityListToDto (List<Treatment> treatmentEntityList);
	
	@Mappings({
	      @Mapping(target="concentrationUnitId", source="unit.id"),
	      @Mapping(target="concentrationUnitLabel", source="unit.unitLabel")
	      })
	TreatmentDTO treatmentEntityToDto (Treatment treatmentEntity);
	
	
}
