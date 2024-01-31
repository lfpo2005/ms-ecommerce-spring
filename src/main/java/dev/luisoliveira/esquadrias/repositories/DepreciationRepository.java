package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.DepreciationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface DepreciationRepository extends JpaRepository<DepreciationModel, UUID> {

    boolean existsByEquipmentAndUser_UserId(String equipment, UUID userId);
    List<DepreciationModel> findAllByUser_UserId(UUID userId);
    @Query("SELECT SUM(d.priceEquipment/120) FROM DepreciationModel d where d.user.userId = :userId")
    BigDecimal priceEquipment(UUID userId);
}
