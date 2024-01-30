package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.ProfitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProfitRepository extends JpaRepository<ProfitModel, UUID> {
    boolean existsByNameAndUser_UserId(String name, UUID userId);

    List<ProfitModel> findAllByUser_UserId(UUID userId);

    @Query("SELECT SUM(p.valuePercentage) FROM ProfitModel p where p.user.userId = :userId")
    Integer sumAllProfitValues(UUID userId);
}
