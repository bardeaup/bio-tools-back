package com.biotools.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PROLIFERATION_EXPERIMENT")
public class ProliferationExperiment {

	@Id
    @GeneratedValue
    private int id;
	
	@Column
	private int proliferationExperimentId;
	
	@Column
	private String cellType;
	
	@Column 
	private Double treatmentValue;
	
	@Column
	private String treatmentUnit;
	
//	@OneToMany
	// List<ProliferationExperimentConditions> conditions; 
	
}
