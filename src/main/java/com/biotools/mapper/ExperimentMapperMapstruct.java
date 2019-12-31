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
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ExperimentMapperMapstruct {
	
	@Mapping(target="conditions", source="conditionList")
	Experiment cellularCountProjectDTOToProliferationExperimentEntity(
			CellularCountProjectDTO cellularCountProjectDTO);
		
	List<Condition> ConditionDTOListToEntity (List<ConditionDTO> conditionDTOList);
	
	Condition ConditionDTOToEntity(ConditionDTO conditionDTO);

	List<CellularCount> cellCountDTOListToEntity (List<CellCountDTO> cellCountDTOList);
	@Mappings({
		@Mapping(target="pd", source="populationDoubling"),
		@Mapping(target="dt", source="doublingTime"),
	})
	CellularCount cellCountDTOToEntity (CellCountDTO cellCountDTO);
	
	List<Treatment> treatmentDTOListToEntity (List<TreatmentDTO> treatmentDTOList);
	
	Treatment treatmentDTOToEntity (TreatmentDTO treatmentDTO);

	// --------- Mapping inverse --------------------

	default Page<CellularCountProjectDTO> proliferationExperimentEntityPageToDto(Page<Experiment> experiments) {
		return experiments.map(this::proliferationExperimentEntityToDto);
	}

	List<CellularCountProjectDTO> proliferationExperimentEntityListToDto(
			List<Experiment> experimentList);
	
	@Mapping(target="conditionList", source="conditions")
	CellularCountProjectDTO proliferationExperimentEntityToDto(
			Experiment experiment);
	
	DetailDTO detailEntityToDto(Detail detail);
		
	List<ConditionDTO> conditionEntityListToDto (List<Condition> conditionList);
	
	ConditionDTO conditionEntityToDto(Condition condition);
	
//	List<CellCountDTO> cellCountEntityListToDto (List<CellularCount> cellCountEntityList);
//	
//	@Mappings({
//	      @Mapping(target="populationDoubling", source="pd"),
//	      @Mapping(target="doublingTime", source="dt")
//	    })
//	CellCountDTO cellCountEntityToDto (CellularCount cellCountEntity);
	
	List<TreatmentDTO> treatmentEntityListToDto (List<Treatment> treatmentEntityList);
	
	@Mappings({
	      @Mapping(target="concentrationUnitId", source="unit.id"),
	      @Mapping(target="concentrationUnitLabel", source="unit.unitLabel")
	      })
	TreatmentDTO treatmentEntityToDto (Treatment treatmentEntity);

}
