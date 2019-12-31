package com.biotools.repository;

import com.biotools.entity.Experiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProliferationExperimentRepository extends JpaRepository<Experiment,Long> {
	Optional<Experiment> findById(Long id);
	
	Optional<Experiment> findByProjectName(String projectName);
		
	Boolean existsByProjectName(String projectName);
	
	List<Experiment> findAllByUserId(Long userId);

	/**
	 * CUSTOM REQUEST :
	 * 		-	Find list of Experiment filtered with various criteria using Pageable parameter
	 * @param pageable parameter used to filter the list to return (number of element, filter on experiment name, sort by creation date...)
	 * @return list of Experiment
	 */
	@Query("select e \n" +
			"from Experiment e \n" +
			"left join Condition c ON e.id = c.experiment.id \n" +
			"left join Treatment t ON c.id = t.condition.id \n" +
			"where e.user.id = :userId \n" +
			"AND (e.projectName LIKE %:experimentName% \n" +
			"           AND c.cellLine LIKE %:cells% \n" +
			"           AND t.name LIKE %:treatment%)\n" +
			"ORDER BY e.creationDate DESC")
	Page<Experiment> findAll(Long userId, String experimentName, String cells, String treatment, Pageable pageable);

	Boolean existsByUserIdAndProjectName(Long userId, String projectName);
	
	Experiment findByUserIdAndProjectName(Long userId, String projectName);
	
	Experiment findByUserIdAndProjectNameIgnoreCase(Long userId, String projectName);
	
	Experiment findByUserIdAndId(Long userId, Long id);
	


}
