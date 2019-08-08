package com.biotools.as;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biotools.ds.ConcentrationDS;
import com.biotools.dto.ConcentrationUnitDTO;
import com.biotools.mapper.ConcentrationUnitMapper;

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
