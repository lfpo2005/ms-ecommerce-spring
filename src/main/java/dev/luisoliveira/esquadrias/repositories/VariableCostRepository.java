package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.VariableCostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VariableCostRepository extends JpaRepository<VariableCostModel, UUID> {
    boolean existsByNameCostsAndUser_UserId(String nameCosts, UUID userId);

    List<VariableCostModel> findAllByUser_UserId(UUID userId);
}
