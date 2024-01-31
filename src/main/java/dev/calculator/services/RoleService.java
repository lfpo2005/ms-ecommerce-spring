package dev.calculator.services;

import dev.calculator.enums.RoleType;
import dev.calculator.models.RoleModel;

import java.util.Optional;

public interface RoleService {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
