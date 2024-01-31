package dev.calculator.services.imp;

import dev.calculator.enums.RoleType;
import dev.calculator.models.RoleModel;
import dev.calculator.repositories.RoleRepository;
import dev.calculator.services.RoleService;
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
