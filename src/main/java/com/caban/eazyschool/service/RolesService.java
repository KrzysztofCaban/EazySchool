package com.caban.eazyschool.service;

import com.caban.eazyschool.model.Roles;
import com.caban.eazyschool.repository.RolesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public Roles findRoleByName(String roleName) {

        return rolesRepository.findByRoleName(roleName);

    }
}
