package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.DepreciationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepreciationRepository extends JpaRepository<DepreciationModel, UUID> {
    boolean existsByEquipmentAndUser_UserId(String equipment, UUID userId);

    List<DepreciationModel> findAllByUser_UserId(UUID userId);
}
