package com.biotools.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.dto.ConditionDTO;
import com.biotools.dto.TreatmentDTO;
import com.biotools.entity.CellularCount;
import com.biotools.entity.Condition;
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
	
	CellularCount cellCountDTOToEntity (CellCountDTO cellCountDTO);
	
	List<Treatment> treatmentDTOListToEntity (List<TreatmentDTO> treatmentDTOList);
	
	Treatment treatmentDTOToEntity (TreatmentDTO treatmentDTO);

}
