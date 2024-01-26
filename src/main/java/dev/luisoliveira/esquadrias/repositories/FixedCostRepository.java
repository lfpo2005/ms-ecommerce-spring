package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.FixedCostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FixedCostRepository extends JpaRepository<FixedCostModel, UUID> {

    boolean existsByNameCostsAndUser_UserId(String nameCosts, UUID userId);
    List<FixedCostModel> findAllByUser_UserId(UUID userId);
}
