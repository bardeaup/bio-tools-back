package com.biotools.rc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.exceptions.UnicityConstraintException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/proliferation-experiment")
public class ProliferationExperimentController {

	@Autowired
	CellCountExperimentAS cellCountExperimentAS;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CellularCountProjectDTO> saveExperiment(@RequestBody CellularCountProjectDTO p)
			throws UnicityConstraintException {

		CellularCountProjectDTO experimentSaved = this.cellCountExperimentAS.saveProliferationExperiment(p);
		return new ResponseEntity<CellularCountProjectDTO>(experimentSaved, HttpStatus.OK);

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
	public ResponseEntity<CellularCountProjectDTO> loadUserExperimentByBame(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "id", required = false) Long id) {
		CellularCountProjectDTO cellularCountProjectDTO;
		if (name != null) {
			cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentByName(name);
		} else {
			cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentById(id);
		}
		return new ResponseEntity<CellularCountProjectDTO>(cellularCountProjectDTO, HttpStatus.OK);

	}

//	@GetMapping
//	@PreAuthorize("hasRole('ROLE_USER')")
//	public ResponseEntity<CellularCountProjectDTO> loadUserExperimentById(@RequestParam(name = "id") Long id) {
//		CellularCountProjectDTO cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentById(id);
//		return new ResponseEntity<CellularCountProjectDTO>(cellularCountProjectDTO, HttpStatus.OK);
//
//	}

}
