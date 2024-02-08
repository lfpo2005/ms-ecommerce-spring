package dev.ecommerce.services;

import dev.ecommerce.enums.RoleType;
import dev.ecommerce.models.RoleModel;

import java.util.Optional;

public interface RoleService {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
