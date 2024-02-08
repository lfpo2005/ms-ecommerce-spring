package dev.ecommerce.services;

import dev.ecommerce.models.CompanyModel;
import dev.ecommerce.models.EmployeeModel;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {


    EmployeeModel save(EmployeeModel employeeId);

    Boolean existsByFullName(String fullName);

    Boolean existsByEmail(String email);

    Optional<EmployeeModel> findById(UUID employeeId);

    Optional<EmployeeModel> findByEmployeeIdAndCompany(UUID employeeId, CompanyModel companyModel);
}
