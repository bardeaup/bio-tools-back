package com.biotools.rest;

import com.biotools.as.CellCountExperimentAS;
import com.biotools.dto.CellularCountConditonDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.exceptions.UnicityConstraintException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/proliferation-experiment")
public class ProliferationExperimentRestController {

	@Autowired
	CellCountExperimentAS cellCountExperimentAS;

	private static final Logger logger = LoggerFactory.getLogger(ProliferationExperimentRestController.class);

	/**
	 * Sauvegarde de l'expérience définie côté IHM, première interface (aucun compte de cellules)
	 * @param p expérience
	 * @return expérience sauvegardée
	 * @throws UnicityConstraintException dans le cas où le nom de l'expérience a déjà été utilisé
	 */
	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CellularCountProjectDTO> saveExperiment(@RequestBody CellularCountProjectDTO p)
			throws UnicityConstraintException {

		logger.info("Class : {} ; Call endpoint POST saveExperiment", ProliferationExperimentRestController.class.getName());

		CellularCountProjectDTO experimentSaved = this.cellCountExperimentAS.saveProliferationExperiment(p);
		return new ResponseEntity<CellularCountProjectDTO>(experimentSaved, HttpStatus.OK);

	}
	
	@PostMapping(path = "count")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CellularCountConditonDTO> saveCellCount(@RequestBody CellularCountConditonDTO cellCountConditionDTO){
		
		return new ResponseEntity<CellularCountConditonDTO>(cellCountExperimentAS.saveCellCount(cellCountConditionDTO), HttpStatus.OK);
	}

	@GetMapping(path = "all")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<CellularCountProjectDTO>> loadExperiment() {

		logger.info("Class : {} ; Call endpoint GET loadExperiment", ProliferationExperimentRestController.class.getName());

		List<CellularCountProjectDTO> cellularCountProjectDTOs = this.cellCountExperimentAS
				.loadExistingUserExperiment();
		if (cellularCountProjectDTOs != null) {
			return new ResponseEntity<List<CellularCountProjectDTO>>(cellularCountProjectDTOs, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<CellularCountProjectDTO>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CellularCountProjectDTO> loadUserExperimentByName(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "id", required = false) Long id) {
		logger.info("Class : {} ; Call endpoint GET loadUserExperimentByName : {}", name, ProliferationExperimentRestController.class.getName());

		CellularCountProjectDTO cellularCountProjectDTO;
		if (name != null) {
			cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentByName(name);
		} else {
			cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentById(id);
		}
		return new ResponseEntity<CellularCountProjectDTO>(cellularCountProjectDTO, HttpStatus.OK);

	}

}
