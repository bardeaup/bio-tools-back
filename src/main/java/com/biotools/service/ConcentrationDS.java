package com.biotools.service;

import com.biotools.entity.ConcentrationUnit;
import com.biotools.repository.ConcentrationUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcentrationDS {
	
	@Autowired
	ConcentrationUnitRepository concentrationUnitRepo;
	
	public List<ConcentrationUnit> getConcentrationUnitReferential(){
		return concentrationUnitRepo.findAll();
	}

}
