package com.biotools.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name= "experiment",
uniqueConstraints = { @UniqueConstraint(columnNames = { "project_name", "user_id" }) })
public class Experiment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="project_name")
	private String projectName;

	@OneToMany(mappedBy = "experiment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Condition> conditions = new ArrayList<Condition>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
