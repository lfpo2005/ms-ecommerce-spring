package dev.msusermanagement.services;


import dev.msusermanagement.enums.RoleType;
import dev.msusermanagement.models.RoleModel;

import java.util.Optional;

public interface RoleService {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
