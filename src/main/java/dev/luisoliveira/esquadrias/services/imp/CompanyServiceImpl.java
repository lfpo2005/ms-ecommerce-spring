package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.repositories.CompanyRepository;
import dev.luisoliveira.esquadrias.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public CompanyModel save(CompanyModel company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<CompanyModel> findById(UUID companyId) {
        return companyRepository.findById(companyId);
    }
}
