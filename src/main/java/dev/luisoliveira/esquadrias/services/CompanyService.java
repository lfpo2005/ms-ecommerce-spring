package dev.luisoliveira.esquadrias.services;


import dev.luisoliveira.esquadrias.models.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService {

    CompanyModel save(CompanyModel company);

    Optional<CompanyModel> findById(UUID companyId);

    Optional<CompanyModel> findByIdWithAddressesAndPhones(UUID companyId);

    Page<CompanyModel> findAll(Pageable pageable);

}
