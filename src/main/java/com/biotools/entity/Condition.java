package com.biotools.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private Integer actualPeriod;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "experiment_id")
	private Experiment experiment;
	
	@OneToMany(mappedBy = "condition",
            cascade = CascadeType.ALL,
    		fetch = FetchType.LAZY,
            orphanRemoval = true)
	private List<CellularCount> cellularCountList = new ArrayList<CellularCount>();
	
	public void addCellularCountList(List<CellularCount> cellCountList){
	    this.cellularCountList.addAll(cellCountList);
	}
	
	@OneToMany(mappedBy = "condition",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
	private List<Treatment> treatmentList= new ArrayList<Treatment>();
	
}
