package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.dtos.EmployeeDto;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.EmployeeModel;
import dev.luisoliveira.esquadrias.services.CompanyService;
import dev.luisoliveira.esquadrias.services.EmployeeService;
import dev.luisoliveira.esquadrias.utils.CryptoUtils;
import dev.luisoliveira.esquadrias.utils.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CompanyService companyService;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("company/{companyId}/registerEmployee")
    public ResponseEntity<Object> registerEmployee(@PathVariable(value = "companyId") UUID companyId,
                                                   @RequestBody @Validated(EmployeeDto.EmployeeView.RegistrationPost.class)
                                                   @JsonView(EmployeeDto.EmployeeView.RegistrationPost.class) EmployeeDto employeeDto) {

        log.debug("POST registerEmployee EmployeeDto received: ------> {}", employeeDto.toString());
        Optional<CompanyModel> companyModelOptional = companyService.findById(companyId);

        try {

            if (employeeService.existsByFullName(employeeDto.getFullName())) {
                log.warn("Fullname {} is Already in use!", employeeDto.getFullName());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Fullname is already in use!");
            }
            if (employeeService.existsByEmail(employeeDto.getEmail())) {
                log.warn("Email {} is Already in use!", employeeDto.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already in use!");
            }
            if (!DateUtil.isValidBirthDate(employeeDto.getBirthDate())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid BirthDate format! Valid format: dd-MM-yyyy");
            }
            if (companyModelOptional.isPresent()) {

                var employeeModel = new EmployeeModel();
                BeanUtils.copyProperties(employeeDto, employeeModel);
                employeeModel.setAdmissionDate(LocalDateTime.now(ZoneId.of("UTC")));
                employeeModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
                employeeModel.setCompany(companyModelOptional.get());

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
