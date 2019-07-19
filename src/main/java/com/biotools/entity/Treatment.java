package com.biotools.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Treatment {

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
	private Condition condition;
}
