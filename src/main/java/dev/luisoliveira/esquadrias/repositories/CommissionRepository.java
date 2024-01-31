package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.CommissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CommissionRepository extends JpaRepository<CommissionModel, UUID> {
    boolean existsByNameAndUser_UserId(String name, UUID userId);
    List<CommissionModel> findAllByUser_UserId(UUID userId);

    @Query("SELECT SUM(c.valuePercentage) FROM CommissionModel c where c.user.userId = :userId")
    Integer sumAllCommissionValues(UUID userId);
}
