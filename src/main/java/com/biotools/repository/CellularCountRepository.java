package com.biotools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biotools.entity.CellularCount;

public interface CellularCountRepository extends JpaRepository<CellularCount,Long> {
	
	List<CellularCount> findByConditionId(Long conditionId);

}
