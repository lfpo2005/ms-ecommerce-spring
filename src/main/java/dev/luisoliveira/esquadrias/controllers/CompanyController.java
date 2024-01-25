package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.CompanyDto;
import dev.luisoliveira.esquadrias.dtos.resp.CompanyRespDTO;
import dev.luisoliveira.esquadrias.models.AddressModel;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.CompanyService;
import dev.luisoliveira.esquadrias.services.UserService;
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
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    ModelMapper modelMapper = new ModelMapper();

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/users/createCompany")
    public ResponseEntity<Object> registerCompany(@RequestBody @Validated(CompanyDto.CompanyView.RegistrationPost.class)
                                                  @JsonView(CompanyDto.CompanyView.RegistrationPost.class) CompanyDto companyDto) {

        log.debug("POST registerCompany CompanyDto received: ------> {}", companyDto.toString());
        try {
            // Obtendo o usuário logado
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
                log.debug("POST registerCompany CompanyModel received: ------> {}", companyModel.toString());
                companyService.save(companyModel);
                log.info("POST registerCompany CompanyModel saved: ------> {}", companyModel.toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(companyModel);
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
            throw  e;
        }
    }
    //TODO: Corrigir loop infinito nos relacionamentos getOneCompany/getAllCompany

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{companyId}")
    public ResponseEntity<Object> getOneCompany(@PathVariable("companyId") UUID companyId) {

        try {

            Optional<CompanyModel> companyModelOptional = companyService.findByIdWithAddressesAndPhones(companyId);
            if (companyModelOptional.isPresent()) {
                CompanyModel company = companyModelOptional.get();
                CompanyRespDTO companyRespDTO = modelMapper.map(company, CompanyRespDTO.class);

             /*   Set<AddressModel> addresses = company.getAddress();
                Set<PhoneModel> phones = company.getPhones();

                Map<String, Object> response = new HashMap<>();
                response.put("company", companyRespDTO);
                response.put("addresses", addresses);
                response.put("phones", phones);*/
                log.info("GET getOneCompany CompanyModel found: ------> {}", companyModelOptional.get().toString());
//                return ResponseEntity.status(HttpStatus.OK).body(response);
                return ResponseEntity.status(HttpStatus.OK).body(companyRespDTO);

            } else {
                log.debug("GET getOneCompany CompanyModel found: ------> {}", companyModelOptional.get().toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllCompany(@PageableDefault(page = 0, size = 10, sort = "companyId", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            Page<CompanyModel> companyModelOptional = companyService.findAll(pageable);
            if (companyModelOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
            }
            return ResponseEntity.ok(companyModelOptional);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }


/*
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PutMapping("/{userId}/updateCompany/{companyId}")
    public ResponseEntity<Object> updateCompany(@RequestBody
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
*/

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