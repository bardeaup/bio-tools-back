package com.biotools.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biotools.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User findUserById(Long id);
	
	Optional<User> findUserByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	User findUserByUsernameAndPassword(String username, String password);
	
}
