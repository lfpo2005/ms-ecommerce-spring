package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
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
       // @Query(value = "SELECT c.*, p.phone_number, p.phone_type FROM tb_companies c LEFT JOIN tb_phones p ON c.company_id = p.company_company_id WHERE c.company_id = :companyId", nativeQuery = true)
        Optional<CompanyModel> findByIdWithAddressesAndPhones(@Param("companyId") UUID companyId);


}
