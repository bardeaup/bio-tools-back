package com.biotools.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biotools.entity.Condition;

public interface ConditionRepository extends JpaRepository<Condition,Long> {
	
	Optional<Condition> findById(Long id);
	
}
