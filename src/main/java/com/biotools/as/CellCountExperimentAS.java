package com.biotools.as;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biotools.ds.CellCountExperimentDS;
import com.biotools.dto.CellCountDTO;
import com.biotools.dto.ProliferationExperimentDTO;

@Service
public class CellCountExperimentAS {

	@Autowired
	CellCountExperimentDS cellCountExperimentDS;

	public ProliferationExperimentDTO saveAndAnalyseExperiement(ProliferationExperimentDTO exp) throws Exception {
		// TODO : enregistrement de l'expérience
		if(exp.getCellCountList() != null 
				&& exp.getCellCountList().get(0) != null && exp.getCellCountList().get(1) != null
				&& exp.getCellCountList().get(0).getCountDate() != null && exp.getCellCountList().get(1).getCountDate() != null) {
			CellCountDTO c0;
			CellCountDTO c1;
			if(exp.getCellCountList().get(0).getCountDate().before(exp.getCellCountList().get(1).getCountDate())) {
				c0 = exp.getCellCountList().get(0);
				c1 = exp.getCellCountList().get(1);
			} else {
				c0 = exp.getCellCountList().get(1);
				c1 = exp.getCellCountList().get(0);
			}
			return this.cellCountExperimentDS.analyseCellCountExperiment(exp.getCellLine(),c0, c1);
		} else {
			throw new Exception("UNCOMPLETE_DATA_SET");
		}
		
				
		
		
		
	}
}
