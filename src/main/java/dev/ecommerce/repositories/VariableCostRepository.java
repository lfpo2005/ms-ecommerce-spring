package dev.ecommerce.repositories;

import dev.ecommerce.models.VariableCostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface VariableCostRepository extends JpaRepository<VariableCostModel, UUID> {
    boolean existsByNameCostsAndUser_UserId(String nameCosts, UUID userId);
    List<VariableCostModel> findAllByUser_UserId(UUID userId);

    @Query("SELECT SUM(v.valueVariableCosts) FROM VariableCostModel v where v.user.userId = :userId")
    BigDecimal valueVariableCosts(UUID userId);
}
