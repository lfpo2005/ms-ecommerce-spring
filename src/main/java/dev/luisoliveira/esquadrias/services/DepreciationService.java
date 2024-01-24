package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.DepreciationModel;

import java.util.List;
import java.util.UUID;

public interface DepreciationService {


    DepreciationModel save(DepreciationModel depreciation);

    Object findAll();

    List<DepreciationModel> findAllByUserId(UUID userId);

    boolean existsByEquipmentAndUserId(String equipment, UUID userId);
}
