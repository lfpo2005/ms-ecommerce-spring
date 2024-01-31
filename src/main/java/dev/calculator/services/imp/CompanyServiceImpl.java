package dev.calculator.services.imp;

import dev.calculator.repositories.CompanyRepository;
import dev.calculator.models.CompanyModel;
import dev.calculator.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Optional<CompanyModel> findByIdWithAddressesAndPhones(UUID companyId) {
        Optional<CompanyModel> companyModelOptional = companyRepository.findByIdWithAddressesAndPhones(companyId);
        if (companyModelOptional.isPresent()) {
            companyModelOptional.get().getAddress().size();
            companyModelOptional.get().getPhones().size();
        }
        return companyModelOptional;
    }

    @Override
    public Page<CompanyModel> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

}
