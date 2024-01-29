package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.CommissionModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionService {
    boolean existsByNameCostsAndUser_UserId(String name, UUID userId);

    CommissionModel save(CommissionModel commissionModel);

    List<CommissionModel> findAllByUserId(UUID userId);

    Optional<CommissionModel> findById(UUID commissionId);

    void delete(CommissionModel fixedCost);
}
