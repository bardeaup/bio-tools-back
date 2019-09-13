package com.biotools.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class CellularCount {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable =  false)
	private int replicatId;

	@Column(nullable =  false)
	private Date date;
	
	@Column (nullable =  false)
	private int quantity;
	
	@Column(columnDefinition = "double default 0")
	private Double pd;
	
	@Column
	private Double dt;
	
	@ManyToOne
	@JoinColumn
	private Condition condition;
	
}
