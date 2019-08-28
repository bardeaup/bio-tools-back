package com.biotools.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Treatment {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column
	private String name;
	
	@Column
	private int concentrationValue;
		
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "condition_id")
	private Condition condition;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private ConcentrationUnit unit;
}
