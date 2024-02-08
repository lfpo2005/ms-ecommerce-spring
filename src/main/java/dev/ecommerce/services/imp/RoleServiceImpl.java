package dev.ecommerce.services.imp;

import dev.ecommerce.enums.RoleType;
import dev.ecommerce.models.RoleModel;
import dev.ecommerce.repositories.RoleRepository;
import dev.ecommerce.services.RoleService;
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
