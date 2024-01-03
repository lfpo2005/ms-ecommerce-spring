package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.enums.RoleType;
import dev.luisoliveira.esquadrias.models.RoleModel;

import java.util.Optional;

public interface RoleService {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
