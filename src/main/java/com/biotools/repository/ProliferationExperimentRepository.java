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
	
	Experiment findByUserIdAndProjectNameIgnoreCase(Long userId, String projectName);
	
	Experiment findByUserIdAndId(Long userId, Long id);
	

	
//	@Query("select e.projectName, d from Experiment e "
//			+ "inner join e.detail d "
//			+ "where lower(e.projectName) like lower(:projectName) AND e.user.id = :userId")
//	Experiment findCustomExperimentByUserIdAndProjectNameIgnoreCase(@Param(value = "userId") Long userId,
//			@Param(value = "projectName") String projectName);

}
