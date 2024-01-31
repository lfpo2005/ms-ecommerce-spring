package dev.calculator.repositories;

import dev.calculator.models.FixedCostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FixedCostRepository extends JpaRepository<FixedCostModel, UUID> {

    boolean existsByNameCostsAndUser_UserId(String nameCosts, UUID userId);
    List<FixedCostModel> findAllByUser_UserId(UUID userId);
    @Query("SELECT SUM(f.valueFixedCosts) FROM FixedCostModel f where f.user.userId = :userId")
    BigDecimal valueFixedCosts(UUID userId);
}
