package dev.ecommerce.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ecommerce.dtos.EmployeeDto;
import dev.ecommerce.services.CompanyService;
import dev.ecommerce.services.EmployeeService;
import dev.ecommerce.utils.UserCompanyValidationUtil;
import dev.ecommerce.models.CompanyModel;
import dev.ecommerce.models.EmployeeModel;
import dev.ecommerce.utils.CryptoUtils;
import dev.ecommerce.utils.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CompanyService companyService;
    @Autowired
    UserCompanyValidationUtil userCompanyValidationUtil;

    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("company/register-employee")
    public ResponseEntity<Object> registerEmployee(@RequestBody
                                                   @Validated(EmployeeDto.EmployeeView.EmployeePost.class)
                                                   @JsonView(EmployeeDto.EmployeeView.EmployeePost.class)
                                                   EmployeeDto employeeDto) {

        log.debug("POST registerEmployee EmployeeDto received: ------> {}", employeeDto.toString());
        try {
            Optional<CompanyModel> companyOptional = userCompanyValidationUtil.validateUserAndCompany();
            if (!companyOptional.isPresent()) {
                log.warn("User not authenticated, not found or company not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated, not found or company not found");
            }
            final ResponseEntity<Object> BAD_REQUEST = validatedUser(employeeDto);
            if (BAD_REQUEST != null) return BAD_REQUEST;

            if (companyOptional.isPresent()) {
                var employeeModel = new EmployeeModel();
                BeanUtils.copyProperties(employeeDto, employeeModel);
                employeeModel.setAdmissionDate(LocalDateTime.now(ZoneId.of("UTC")));
                employeeModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
                employeeModel.setCompany(companyOptional.get());

                try {
                    String encryptedCpf = CryptoUtils.encrypt(employeeDto.getCpf());
                    employeeModel.setCpf(encryptedCpf);
                } catch (Exception e) {
                    log.error("Error encrypting CPF", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encrypting CPF");
                }

                log.info("POST registerEmployee EmployeeDto received: ------> {}", employeeDto.toString());
                employeeService.save(employeeModel);
                log.trace("Employee {} registered successfully!", employeeModel.getFullName());
                return ResponseEntity.status(HttpStatus.CREATED).body(employeeModel);
            } else {
                log.warn("Company not found!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Company not found!");
            }
        } catch (Exception e) {
            log.error("POST registerEmployee EmployeeDto error: ------> {}", e.getMessage());
            throw e;
        }
    }

    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PutMapping("company/{employeeId}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("employeeId") UUID employeeId,
                                                 @RequestBody
                                                 @Validated(EmployeeDto.EmployeeView.EmployeePut.class)
                                                 @JsonView(EmployeeDto.EmployeeView.EmployeePut.class)
                                                 EmployeeDto employeeDto) {

        log.debug("PUT updateEmployee EmployeeDto received: ------> {}", employeeDto.toString());

        try {
            Optional<CompanyModel> companyOptional = userCompanyValidationUtil.validateUserAndCompany();
            if (!companyOptional.isPresent()) {
                log.warn("User not authenticated, not found or company not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated, not found or company not found");
            }
            if (employeeId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The employeeId field must not be null");
            }
            if (!DateUtil.isValidBirthDate(employeeDto.getBirthDate())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid BirthDate format! Valid format: dd-MM-yyyy");
            }
            Optional<EmployeeModel> employeeModelOptional = employeeService.findByEmployeeIdAndCompany(employeeId, companyOptional.get());
            if (!employeeModelOptional.isPresent() || employeeModelOptional.get().getActive() == false) {
                log.warn("Employee not found in the company!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Employee not found in the company!");
            }
            EmployeeModel employeeModel = employeeModelOptional.get();
            employeeModel.setFullName(employeeDto.getFullName());
            employeeModel.setBirthDate(employeeDto.getBirthDate());
            employeeModel.setWorkCardNumber(employeeDto.getWorkCardNumber());
            employeeModel.setFunction(employeeDto.getFunction());
            employeeModel.setSalary(employeeDto.getSalary());
            employeeModel.setSocialCharges(employeeDto.getSocialCharges());
            employeeModel.setDescription(employeeDto.getDescription());
            employeeModel.setPhoneNumber(employeeDto.getPhoneNumber());
            employeeModel.setEmail(employeeDto.getEmail());
            employeeModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            employeeModel.setCompany(companyOptional.get());

            log.info("PUT updateEmployee EmployeeDto received: ------> {}", employeeDto.toString());
            employeeService.save(employeeModel);
            log.trace("Employee {} updated successfully!", employeeModel.getFullName());
            return ResponseEntity.status(HttpStatus.OK).body(employeeModel);

        } catch (Exception e) {
            log.error("PUT updateEmployee EmployeeDto error: ------> {}", e.getMessage());
            throw e;
        }
    }

    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PutMapping("company/{employeeId}/reason-dismissal")
    public ResponseEntity<Object> reasonDismissal(@PathVariable("employeeId") UUID employeeId,
                                                  @RequestBody
                                                  @Validated(EmployeeDto.EmployeeView.ReasonDismissalPut.class)
                                                  @JsonView(EmployeeDto.EmployeeView.ReasonDismissalPut.class)
                                                  EmployeeDto employeeDto) {

        log.debug("PUT reasonDismissal EmployeeDto received: ------> {}", employeeDto.toString());

        try {
            Optional<CompanyModel> companyOptional = userCompanyValidationUtil.validateUserAndCompany();
            if (!companyOptional.isPresent()) {
                log.warn("User not authenticated, not found or company not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated, not found or company not found");
            }
            if (employeeId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The employeeId field must not be null");
            }
            Optional<EmployeeModel> employeeModelOptional = employeeService.findByEmployeeIdAndCompany(employeeId, companyOptional.get());
            if (!employeeModelOptional.isPresent()) {
                log.warn("Employee not found in the company!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Employee not found in the company!");
            }
            EmployeeModel employeeModel = employeeModelOptional.get();
            employeeModel.setReasonDismissal(employeeDto.getReasonDismissal());
            employeeModel.setDismissalDate(LocalDateTime.now(ZoneId.of("UTC")));
            employeeModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            employeeModel.setActive(false);
            employeeModel.setCompany(companyOptional.get());
            log.info("PUT reasonDismissal EmployeeDto received: ------> {}", employeeDto.toString());
            employeeService.save(employeeModel);
            return ResponseEntity.status(HttpStatus.OK).body(employeeModel);
        } catch (Exception e) {
            log.error("PUT reasonDismissal EmployeeDto error: ------> {}", e.getMessage());
            throw e;
        }
    }

    private ResponseEntity<Object> validatedUser(EmployeeDto employeeDto) {
        if (!DateUtil.isValidBirthDate(employeeDto.getBirthDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid BirthDate format! Valid format: dd-MM-yyyy");
        }
        if (employeeService.existsByFullName(employeeDto.getFullName())) {
            log.warn("Fullname {} is Already in use!", employeeDto.getFullName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Fullname is already in use!");
        }
        if (employeeService.existsByEmail(employeeDto.getEmail())) {
            log.warn("Email {} is Already in use!", employeeDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already in use!");
        }
        return null;
    }

}
