package dev.ecommerce.repositories;

import dev.ecommerce.enums.RoleType;
import dev.ecommerce.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

       Optional<RoleModel> findByRoleName(RoleType name);
}
