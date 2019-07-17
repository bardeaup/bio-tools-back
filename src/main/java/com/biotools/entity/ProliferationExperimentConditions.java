package com.biotools.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ProliferationExperimentConditions {

	@Id
    @GeneratedValue
    private int id;
	
	@Column
	private int proliferationExperimentId;
	
	@Column
	private String name;
	
	@Column
	private Date creationDate;
	
//	@OneToMany
//	private List<CellularCount> cellularCountList;
	
}
