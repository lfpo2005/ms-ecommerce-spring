package dev.calculator.repositories;

import dev.calculator.enums.RoleType;
import dev.calculator.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

       Optional<RoleModel> findByRoleName(RoleType name);
}
