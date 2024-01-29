package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.ProfitModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfitService {
    boolean existsByNameCostsAndUser_UserId(String name, UUID userId);

    ProfitModel save(ProfitModel profitModel);

    List<ProfitModel> findAllByUserId(UUID userId);

    Optional<ProfitModel> findById(UUID profitId);

    void delete(ProfitModel fixedCost);
}
