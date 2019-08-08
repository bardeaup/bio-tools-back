package com.biotools.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biotools.entity.ConcentrationUnit;

public interface ConcentrationUnitRepository extends JpaRepository<ConcentrationUnit, Long>{
	
	ConcentrationUnit findById(String id);

}
