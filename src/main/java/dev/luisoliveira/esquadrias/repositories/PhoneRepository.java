package dev.luisoliveira.storejava.repositories;

import dev.luisoliveira.storejava.models.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<PhoneModel, UUID> {
}
