package com.biotools.as;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biotools.ds.CellCountExperimentDS;
import com.biotools.dto.CellularCountProjectDTO;

@Service
public class CellCountExperimentAS {

	@Autowired
	CellCountExperimentDS cellCountExperimentDS;

	public CellularCountProjectDTO saveAndAnalyseExperiement(CellularCountProjectDTO p) throws Exception {
		
		if(p != null && p.getConditionList() != null && !p.getConditionList().isEmpty()) {
			p.setConditionList(this.cellCountExperimentDS.analyseCellCountExperiment(p.getConditionList()));
			this.cellCountExperimentDS.saveCellCountExperiment(p);
			return p;
		} else {
			throw new Exception("UNCOMPLETE_DATA_SET");
		}
				
	}
	
	public List<CellularCountProjectDTO> loadExistingUserExperiment(){
		cellCountExperimentDS.loadUserExistingExperiment();
		return null;
	}
}
