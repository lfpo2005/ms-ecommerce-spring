package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.enums.RoleType;
import dev.luisoliveira.esquadrias.models.RoleModel;
import dev.luisoliveira.esquadrias.repositories.RoleRepository;
import dev.luisoliveira.esquadrias.services.RoleService;
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
