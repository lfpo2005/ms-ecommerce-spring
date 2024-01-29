package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.TaxesModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxesService {
    boolean existsByNameCostsAndUser_UserId(String name, UUID userId);

    TaxesModel save(TaxesModel taxesModel);

    List<TaxesModel> findAllByUserId(UUID userId);

    Optional<TaxesModel> findById(UUID taxesId);

    void delete(TaxesModel taxesModel);
}
