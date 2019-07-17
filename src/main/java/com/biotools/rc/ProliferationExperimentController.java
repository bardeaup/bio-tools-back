package com.biotools.rc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biotools.as.CellCountExperimentAS;
import com.biotools.dto.ProliferationExperimentDTO;

@RestController
@RequestMapping("proliferation-experiment")
public class ProliferationExperimentController {
	
	@Autowired
	CellCountExperimentAS cellCountExperimentAS; 
	
	@PostMapping
	public ResponseEntity<ProliferationExperimentDTO> saveAndAnalyseExperiement(
			@RequestBody ProliferationExperimentDTO exp){
		try {
			exp = this.cellCountExperimentAS.saveAndAnalyseExperiement(exp);
			return new ResponseEntity<ProliferationExperimentDTO>(exp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ProliferationExperimentDTO>(HttpStatus.BAD_REQUEST);
		}	
	}

}
