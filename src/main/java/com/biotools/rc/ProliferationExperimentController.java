package com.biotools.rc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biotools.as.CellCountExperimentAS;
import com.biotools.dto.CellularCountConditonDTO;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.exceptions.UnicityConstraintException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/proliferation-experiment")
public class ProliferationExperimentController {

	@Autowired
	CellCountExperimentAS cellCountExperimentAS;

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
		CellularCountProjectDTO cellularCountProjectDTO;
		if (name != null) {
			cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentByName(name);
		} else {
			cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentById(id);
		}
		if(cellularCountProjectDTO == null){
			return new ResponseEntity<>(cellularCountProjectDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cellularCountProjectDTO, HttpStatus.OK);
		}

	}

	@GetMapping(path = "history")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Page<CellularCountProjectDTO>> loadUserExperimentHistory(
			@RequestParam int page,
			@RequestParam int size,
			@RequestParam Optional<String> experimentName,
			@RequestParam Optional<String> cells,
			@RequestParam Optional<String> treatment) {
		return new ResponseEntity<>(cellCountExperimentAS.loadUserExperimentHistory(page, size, experimentName, cells, treatment),
				HttpStatus.OK);
	}
}
