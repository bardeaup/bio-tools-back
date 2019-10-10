package com.biotools.as;

import com.biotools.dto.ConcentrationUnitDTO;
import com.biotools.mapper.ConcentrationUnitMapper;
import com.biotools.service.ConcentrationDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcentrationAS {
	
	@Autowired
	ConcentrationDS concentrationDS;
	
	@Autowired
	ConcentrationUnitMapper concentrationUnitMapper;
	
	
	public List<ConcentrationUnitDTO> getConcentrationUnitReferential(){
		return concentrationUnitMapper.concentrationUnitEntityListToDTO(concentrationDS.getConcentrationUnitReferential());
	}

}
