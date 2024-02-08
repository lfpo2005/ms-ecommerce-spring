package dev.ecommerce.services.imp;

import dev.ecommerce.repositories.EmployeeRepository;
import dev.ecommerce.models.CompanyModel;
import dev.ecommerce.models.EmployeeModel;
import dev.ecommerce.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
