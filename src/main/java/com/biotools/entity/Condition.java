package com.biotools.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Condition {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column
	private String cellLine;
	
	@Column
	private Double initialPopulationDoubling;
	
	@Column
	private Date creationDate;
	

	@ManyToOne
	@JoinColumn(name = "experiment_id")
	private Experiment experiment;
	
	@OneToMany(mappedBy = "condition",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
	private List<CellularCount> cellularCountList = new ArrayList<CellularCount>();
	
	@OneToMany(mappedBy = "condition",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
	private List<Treatment> treatmentList= new ArrayList<Treatment>();
	
}
