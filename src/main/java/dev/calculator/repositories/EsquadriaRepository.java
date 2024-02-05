package dev.calculator.repositories;

import dev.calculator.models.EsquadriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EsquadriaRepository extends JpaRepository<EsquadriaModel, UUID> {
}
