package com.biotools.mapper;

import org.mapstruct.Mapper;

import com.biotools.dto.CellCountDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.dto.ConditionDTO;
import com.biotools.dto.TreatmentDTO;
import com.biotools.entity.CellularCount;
import com.biotools.entity.Condition;
import com.biotools.entity.ProliferationExperiment;
import com.biotools.entity.Treatment;

@Mapper(componentModel ="spring")
public interface ExperimentMapper {

	ProliferationExperiment cellularCountProjectDTOToProliferationExperimentEntity(CellularCountProjectDTO cellularCountProjectDTO);
	Condition conditionDTOToConditionEntity(ConditionDTO conditionDTO);
	CellularCount cellCountDTOToCellularCountEntity(CellCountDTO cellCountDTO);
	Treatment treatmentDTOToTreatmentEntity(TreatmentDTO treatmentDTO);
	
}
