package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.dtos.CompanyDto;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.CompanyService;
import dev.luisoliveira.esquadrias.services.UserService;
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
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/users/{companyId}/createCompany")
    public ResponseEntity<Object> registerCompany(@PathVariable(value = "companyId") UUID userId,
                                                  @RequestBody @Validated(CompanyDto.CompanyView.RegistrationPost.class)
                                                  @JsonView(CompanyDto.CompanyView.RegistrationPost.class) CompanyDto companyDto) {

        log.debug("POST registerCompany CompanyDto received: ------> {}", companyDto.toString());
        Optional<UserModel> userModelOptional = userService.findById(userId);

        try {
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (companyDto.getCompanyId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The companyId field must be null");
            }
            var companyModel = new CompanyModel();
            BeanUtils.copyProperties(companyDto, companyModel);
            companyModel.setResponsibleUser(userModelOptional.get());
            companyModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
            companyModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            companyService.save(companyModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(companyModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{companyId}")
    public ResponseEntity<Object> getCompany(@PathVariable UUID companyId) {
        try {
            Optional<CompanyModel> companyModelOptional = companyService.findById(companyId);
            if (companyModelOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
            }
            return ResponseEntity.ok(companyModelOptional);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PutMapping("/{userId}/updateCompany/{companyId}")
    public ResponseEntity<Object> updateCompany(@PathVariable UUID companyId,
                                                @RequestBody
                                                @Validated(CompanyDto.CompanyView.RegistrationPost.class)
                                                @JsonView(CompanyDto.CompanyView.RegistrationPost.class) CompanyDto companyDto) {
        try {
            Optional<CompanyModel> companyModelOptional = companyService.findById(companyId);
            if (companyModelOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
            }
            var companyModel = new CompanyModel();
            BeanUtils.copyProperties(companyDto, companyModel);
            companyService.save(companyModel);
            return ResponseEntity.ok(companyModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{companyId}/deactivate-company")
    public ResponseEntity<Object> deactivateCompany(@PathVariable UUID companyId) {
        try {
            Optional<CompanyModel> companyModelOptional = companyService.findById(companyId);
            if (companyModelOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
            }

            var companyModel = new CompanyModel();
            companyModel.setActive(false);
            companyModel.setDeleted(true);
            companyService.save(companyModel);
            return ResponseEntity.ok("Company deactivated successfully");
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }
}