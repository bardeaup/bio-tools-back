package com.biotools.rc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biotools.as.CellCountExperimentAS;
import com.biotools.dto.CellularCountProjectDTO;
import com.biotools.security.services.UserPrinciple;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/proliferation-experiment")
public class ProliferationExperimentController {
	
	@Autowired
	CellCountExperimentAS cellCountExperimentAS; 
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CellularCountProjectDTO> saveAndAnalyseExperiement(
			@RequestBody CellularCountProjectDTO p){
		
		// Récupération de l'id User
		UserPrinciple principal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long idUser = principal.getId();
		
		try {
			this.cellCountExperimentAS.saveAndAnalyseExperiement(p);
			return new ResponseEntity<CellularCountProjectDTO>(p, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CellularCountProjectDTO>(HttpStatus.BAD_REQUEST);
		}	
	}

}
