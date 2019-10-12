package com.biotools.config;

import com.biotools.entity.Role;
import com.biotools.entity.RoleName;
import com.biotools.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitDatas {

    private final RoleRepository roleRepository;

    @Autowired
    public InitDatas(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles(){
        if(!roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent()){
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
        if(!roleRepository.findByName(RoleName.ROLE_PM).isPresent()){
            roleRepository.save(new Role(RoleName.ROLE_PM));
        }
        if(!roleRepository.findByName(RoleName.ROLE_USER).isPresent()){
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }
    }
}
