package dev.calculator.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.calculator.configs.security.UserDetailsImpl;
import dev.calculator.dtos.CompanyDto;
import dev.calculator.dtos.resp.CompanyRespDTO;
import dev.calculator.services.CompanyService;
import dev.calculator.services.UserService;
import dev.calculator.models.CompanyModel;
import dev.calculator.models.UserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    ModelMapper modelMapper = new ModelMapper();

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/users/create-company")
    public ResponseEntity<Object> registerCompany(@RequestBody @Validated(CompanyDto.CompanyView.CompanyPost.class)
                                                  @JsonView(CompanyDto.CompanyView.CompanyPost.class) CompanyDto companyDto) {

        log.debug("POST registerCompany CompanyDto received: ------> {}", companyDto.toString());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UserModel loggedInUser = userService.findById(userDetails.getUserId()).get();
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (loggedInUser.getCompany() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already has a company");
            } else {
                var companyModel = new CompanyModel();
                BeanUtils.copyProperties(companyDto, companyModel);
                companyModel.setResponsibleUser(loggedInUser);
                companyModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
                companyModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
                log.info("POST registerCompany CompanyModel received: ------> {}", companyModel.toString());
                companyService.save(companyModel);
                log.info("POST registerCompany CompanyModel saved: ------> {}", companyModel.toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(companyModel);
            }
        } catch (Exception e) {
            log.error("Specific error occurred, errorId: {}",  e);
            throw e;

        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{companyId}")
    public ResponseEntity<Object> getOneCompany(@PathVariable("companyId") UUID companyId) {

        try {
            Optional<CompanyModel> companyModelOptional = companyService.findByIdWithAddressesAndPhones(companyId);
            if (companyModelOptional.isPresent()) {
                CompanyModel company = companyModelOptional.get();
                CompanyRespDTO companyRespDTO = modelMapper.map(company, CompanyRespDTO.class);
                log.info("GET getOneCompany CompanyModel found: ------> {}", companyModelOptional.get().toString());
                return ResponseEntity.status(HttpStatus.OK).body(companyRespDTO);
            } else {
                log.info("GET getOneCompany CompanyModel found: ------> {}", companyModelOptional.get().toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllCompany(@PageableDefault(page = 0, size = 10, sort = "companyId", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<CompanyModel> companyModelOptional = companyService.findAll(pageable);
            if (companyModelOptional == null) {
                log.info("GET getAllCompany CompanyModel found: ------> {}", companyModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
            }
            return ResponseEntity.ok(companyModelOptional);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;

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
            log.info("Company deactivated successfully");
            return ResponseEntity.ok("Company deactivated successfully: -------> " + companyModelOptional.toString());
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}