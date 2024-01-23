package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.EmployeeModel;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

//    Optional<EmployeeModel> findById(UUID employeeId);

    EmployeeModel save(EmployeeModel employeeId);

    boolean existsByFullName(String fullName);

    boolean existsByEmail(String email);
}
