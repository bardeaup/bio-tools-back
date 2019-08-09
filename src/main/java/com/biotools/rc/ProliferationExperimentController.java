package com.biotools.rc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biotools.as.CellCountExperimentAS;
import com.biotools.dto.CellularCountProjectDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/proliferation-experiment")
public class ProliferationExperimentController {
	
	@Autowired
	CellCountExperimentAS cellCountExperimentAS; 
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CellularCountProjectDTO> saveAndAnalyseExperiment(
			@RequestBody CellularCountProjectDTO p){
		
		try {
			this.cellCountExperimentAS.saveAndAnalyseExperiement(p);
			return new ResponseEntity<CellularCountProjectDTO>(p, HttpStatus.OK);
		} catch (Exception e) {
			// TODO : gestion des erreurs, renvoyer un objet commun en retour de chaque API avec erreur et content
			return new ResponseEntity<CellularCountProjectDTO>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<CellularCountProjectDTO>> loadExperiment(){
		List<CellularCountProjectDTO> cellularCountProjectDTOs = this.cellCountExperimentAS.loadExistingUserExperiment();
		if(cellularCountProjectDTOs != null) {
			return new ResponseEntity<List<CellularCountProjectDTO>>(cellularCountProjectDTOs, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<CellularCountProjectDTO>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(path="/{name}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CellularCountProjectDTO> loadUserExperimentByBame(@PathVariable("name") String name){
		CellularCountProjectDTO cellularCountProjectDTO = this.cellCountExperimentAS.loadExistingUserExperimentByName(name);
		return new ResponseEntity<CellularCountProjectDTO>(cellularCountProjectDTO, HttpStatus.OK);
		
	}

}
