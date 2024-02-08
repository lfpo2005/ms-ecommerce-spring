package dev.ecommerce.repositories;

import dev.ecommerce.models.CompanyModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyModel, UUID> {


        @EntityGraph(attributePaths = "phones", type = EntityGraph.EntityGraphType.FETCH)
        Optional<CompanyModel> findById(UUID companyId);

        @Query(value = "SELECT u FROM CompanyModel u JOIN FETCH u.phones WHERE u.companyId = :companyId")
        Optional<CompanyModel> findByIdWithAddressesAndPhones(@Param("companyId") UUID companyId);


}
