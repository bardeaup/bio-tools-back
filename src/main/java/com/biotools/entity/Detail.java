package com.biotools.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Detail {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column
	private String cultureMedia;
	
	@Column
	private String growthFactor;
	
	@Column
	private String antibiotic;
	
	@Column
	private Integer dioxygenPercentage;
	
	@Column
	private Integer temperature;
	
	@Column
	private Integer conditionReplicat;
			
}
