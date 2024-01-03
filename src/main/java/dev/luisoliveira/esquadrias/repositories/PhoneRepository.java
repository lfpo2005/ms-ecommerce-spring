package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<PhoneModel, UUID> {
}
