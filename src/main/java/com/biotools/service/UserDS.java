package com.biotools.service;

import com.biotools.entity.User;
import com.biotools.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDS {

    @Autowired
    private UserRepository userRepository;

    public User findUserByUsername(String name){
        return this.userRepository.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException(name));
    }
}
