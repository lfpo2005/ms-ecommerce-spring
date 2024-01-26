package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.EmployeeModel;
import dev.luisoliveira.esquadrias.repositories.EmployeeRepository;
import dev.luisoliveira.esquadrias.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public Optional<EmployeeModel> findById(UUID employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Optional<EmployeeModel> findByEmployeeIdAndCompany(UUID employeeId, CompanyModel companyModel) {
        return employeeRepository.findByEmployeeIdAndCompany(employeeId, companyModel);
    }

    @Override
    public EmployeeModel save(EmployeeModel employeeModel) {
        employeeRepository.save(employeeModel);
        return employeeModel;
    }

    @Override
    public Boolean existsByFullName(String fullName) {
        return employeeRepository.existsByFullName(fullName);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
}
