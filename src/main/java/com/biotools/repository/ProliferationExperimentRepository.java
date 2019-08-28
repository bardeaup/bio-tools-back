package com.biotools.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biotools.entity.Experiment;

public interface ProliferationExperimentRepository extends JpaRepository<Experiment,Long> {
	Optional<Experiment> findById(Long id);
	
	Optional<Experiment> findByProjectName(String projectName);
	
	Boolean existsByProjectName(String projectName);
	
	List<Experiment> findAllByUserId(Long userId);
	
	Boolean existsByUserIdAndProjectName(Long userId, String projectName);
	
	Experiment findByUserIdAndProjectName(Long userId, String projectName);
	
	Experiment findByUserIdAndId(Long userId, Long id);

}
