package com.biotools.ds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biotools.entity.ConcentrationUnit;
import com.biotools.repository.ConcentrationUnitRepository;

@Service
public class ConcentrationDS {
	
	@Autowired
	ConcentrationUnitRepository concentrationUnitRepo;
	
	public List<ConcentrationUnit> getConcentrationUnitReferential(){
		return concentrationUnitRepo.findAll();
	}

}
