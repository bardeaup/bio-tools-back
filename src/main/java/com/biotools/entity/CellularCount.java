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

	@Column
	private Date beginDate;
	
	@Column
	private Date endDate;
	
	@Column 
	private int initialCellQuantity;
	
	@Column 
	private int finalCellQuantity;
	
	@Column
	private Double pd;
	
	@Column
	private Double dt;
	
	@Column
	private Double finalPd;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "condition_id")
	@ManyToOne
	@JoinColumn
	private Condition condition;
	
}
