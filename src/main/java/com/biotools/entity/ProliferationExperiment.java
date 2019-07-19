package com.biotools.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class ProliferationExperiment {

	@Id
    @GeneratedValue
    private Long id;
	
	@Column
	private String projectName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "experiment")
	private List<Condition> conditions = new ArrayList<Condition>();
	
	@ManyToOne
	private User user;

}
