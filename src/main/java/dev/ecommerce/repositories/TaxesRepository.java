package dev.ecommerce.repositories;

import dev.ecommerce.models.TaxesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaxesRepository extends JpaRepository<TaxesModel, UUID> {
    boolean existsByNameAndUser_UserId(String name, UUID userId);
    List<TaxesModel> findAllByUser_UserId(UUID userId);

    @Query("SELECT SUM(t.valuePercentage) FROM TaxesModel t where t.user.userId = :userId")
    Integer sumAllTaxesValues(UUID userId);
}
