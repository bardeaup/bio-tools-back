package com.biotools.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CellularCount {

	@Id
    @GeneratedValue
    private int id;
	
	@Column
	private int conditionId;
	
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
	
}
