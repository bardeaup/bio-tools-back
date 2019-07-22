package com.biotools.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biotools.entity.ProliferationExperiment;

public interface ProliferationExperimentRepository extends JpaRepository<ProliferationExperiment,Long> {
	Optional<ProliferationExperiment> findById(Long id);
	
	Optional<ProliferationExperiment> findByProjectName(String projectName);
	
	Boolean existsByProjectName(String projectName);
	
	ProliferationExperiment saveProliferationExperiment(ProliferationExperiment experiment);

}
