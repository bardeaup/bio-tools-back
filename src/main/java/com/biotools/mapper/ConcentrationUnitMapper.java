package com.biotools.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.biotools.dto.ConcentrationUnitDTO;
import com.biotools.entity.ConcentrationUnit;

@Mapper(componentModel = "spring")
public interface ConcentrationUnitMapper {

	List<ConcentrationUnitDTO> concentrationUnitEntityListToDTO(List<ConcentrationUnit> concentrationUnitList);
	
	@Mapping(target="unitId", source="id")
	ConcentrationUnitDTO concentrationUnitEntityToDTO(ConcentrationUnit concentrationUnit);
}
