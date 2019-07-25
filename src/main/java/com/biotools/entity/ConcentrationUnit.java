package com.biotools.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ConcentrationUnit {

	@Id
    @GeneratedValue
    private Long id;

	@Column
	private String unit;
		
}
