package com.akveo.bundlejava.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getDefaultRole() {
        return roleRepository.findDefault();
    }

}
