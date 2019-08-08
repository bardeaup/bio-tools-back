package com.biotools.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class ConcentrationUnit {

    @Id
	@Column(unique = true)
	private String id;
	
	@Column
	private String unitLabel;
	
	@Column
	private BigInteger numericValue;
	
}
