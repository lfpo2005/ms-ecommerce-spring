package dev.luisoliveira.esquadrias.services;


import dev.luisoliveira.esquadrias.models.CompanyModel;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService {

    CompanyModel save(CompanyModel company);

    Optional<CompanyModel> findById(UUID companyId);
}
