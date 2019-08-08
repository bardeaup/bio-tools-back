package com.biotools.rc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biotools.as.ConcentrationAS;
import com.biotools.dto.ConcentrationUnitDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/concentration")
public class ConcentrationController {

	@Autowired
	ConcentrationAS concentrationAS;

	@GetMapping(path = "referential")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<ConcentrationUnitDTO>> getConcentrationUnitRef(){

		return new ResponseEntity<List<ConcentrationUnitDTO>>(concentrationAS.getConcentrationUnitReferential(), HttpStatus.OK);
	}

}
