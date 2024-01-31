package dev.calculator.repositories;

import dev.calculator.models.CompanyModel;
import dev.calculator.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, UUID> {
    Boolean existsByEmail(String email);
    Boolean existsByFullName(String fullName);
    Optional<EmployeeModel> findByEmployeeIdAndCompany(UUID employeeId, CompanyModel companyModel);
    @Query("SELECT SUM(e.salary + e.socialCharges) FROM EmployeeModel e where e.company.responsibleUser.userId = :userId")
    BigDecimal valueEmployees(UUID userId);
}
