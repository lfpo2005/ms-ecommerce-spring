package dev.msusermanagement.repositories;

import dev.msusermanagement.models.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<PhoneModel, UUID> {
}
