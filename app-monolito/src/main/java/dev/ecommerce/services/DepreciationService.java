package dev.ecommerce.services;

import dev.ecommerce.models.DepreciationModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepreciationService {


    DepreciationModel save(DepreciationModel depreciation);

    Object findAll();

    List<DepreciationModel> findAllByUserId(UUID userId);

    boolean existsByEquipmentAndUserId(String equipment, UUID userId);

    Optional<DepreciationModel> findById(UUID depreciationId);
}
