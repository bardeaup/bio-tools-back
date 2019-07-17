package com.biotools.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class LoginFormDTO implements Serializable {

	private static final long serialVersionUID = 8378660703797355782L;
	private int userId;
	private String username;
	private String password;
	private String email;
	private Set<String> role;
}
