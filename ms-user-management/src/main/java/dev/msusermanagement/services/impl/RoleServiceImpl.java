package dev.msusermanagement.services.impl;

import dev.msusermanagement.enums.RoleType;
import dev.msusermanagement.models.RoleModel;
import dev.msusermanagement.repositories.RoleRepository;
import dev.msusermanagement.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
