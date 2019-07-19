package com.biotools.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Condition {

	@Id
    @GeneratedValue
    private Long id;

	@Column
	private String cellLine;
	
	@Column
	private Double initialPopulationDoubling;
	
	@Column
	private Date creationDate;
	
	@ManyToOne
	private ProliferationExperiment experiment;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "condition")
	private List<CellularCount> cellularCountList = new ArrayList<CellularCount>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "condition")
	private List<Treatment> treatmentList= new ArrayList<Treatment>();
	
}
