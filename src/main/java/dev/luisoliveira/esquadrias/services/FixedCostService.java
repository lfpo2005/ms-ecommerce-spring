package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.FixedCostModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FixedCostService {

    FixedCostModel save(FixedCostModel fixedCost);

    boolean existsByNameCostsAndUser_UserId(String nameCosts, UUID userId);

    List<FixedCostModel> findAllByUserId(UUID userId);

    Optional<FixedCostModel> findById(UUID fixedCostId);

    void delete(FixedCostModel fixedCost);
}
