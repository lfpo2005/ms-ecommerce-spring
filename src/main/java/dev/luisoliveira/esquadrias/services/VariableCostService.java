package dev.luisoliveira.esquadrias.services;


import dev.luisoliveira.esquadrias.models.VariableCostModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VariableCostService {
    VariableCostModel save(VariableCostModel variableCostModel);

    boolean existsByNameCostsAndUser_UserId(String nameCosts, UUID userId);

    List<VariableCostModel> findAllByUserId(UUID userId);

    Optional<VariableCostModel> findById(UUID variableCostId);

    void delete(VariableCostModel variableCostModel);
}
