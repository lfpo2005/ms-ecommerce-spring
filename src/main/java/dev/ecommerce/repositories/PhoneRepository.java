package dev.ecommerce.repositories;

import dev.ecommerce.models.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<PhoneModel, UUID> {
}
