package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.AddressDto;
import dev.luisoliveira.esquadrias.enums.AddressType;
import dev.luisoliveira.esquadrias.models.AddressModel;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.EmployeeModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.AddressService;
import dev.luisoliveira.esquadrias.services.CompanyService;
import dev.luisoliveira.esquadrias.services.EmployeeService;
import dev.luisoliveira.esquadrias.services.UserService;
import dev.luisoliveira.esquadrias.utils.UserCompanyValidationUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserCompanyValidationUtil userCompanyValidationUtil;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/register-user-address")
    public ResponseEntity<Object> registerUserAddress(@RequestBody @Validated(AddressDto.AddressView.RegistrationPost.class)
                                                  @JsonView(AddressDto.AddressView.RegistrationPost.class) AddressDto addressDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel loggedInUser = userService.findById(userDetails.getUserId()).get();
        log.debug("POST registerAddress AddressDto received: ------> {}", addressDto.toString());

        Optional<UserModel> userModelOptional = userService.findById(loggedInUser.getUserId());
        try {
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (addressDto.getAddressId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The addressId field must be null");
            }
            var addressModel = new AddressModel();
            BeanUtils.copyProperties(addressDto, addressModel);
            addressModel.setType(AddressType.RESIDENTIAL);
            addressModel.setUser(userModelOptional.get());
            addressService.save(addressModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(addressModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/employee/{employeeId}/register-employee-address")
    public ResponseEntity<Object> registerEmployeeAddress(@PathVariable(value = "employeeId") UUID employeeId,
                                                         @RequestBody @Validated(AddressDto.AddressView.RegistrationPost.class)
                                                         @JsonView(AddressDto.AddressView.RegistrationPost.class) AddressDto addressDto) {
        log.debug("POST registerEmployeeAddress AddressDto received: ------> {}", addressDto.toString());
        Optional<EmployeeModel> employeeModelOptional = employeeService.findById(employeeId);
        try {
            if (!employeeModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
            if (addressDto.getAddressId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The addressId field must be null");
            }
            var addressModel = new AddressModel();
            BeanUtils.copyProperties(addressDto, addressModel);
            addressModel.setType(AddressType.EMPLOYEE);
            addressModel.setCompany(employeeModelOptional.get().getCompany());
            addressService.save(addressModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(addressModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/company/register-company-address")
    public ResponseEntity<Object> registerCompanyAddress(@RequestBody @Validated(AddressDto.AddressView.RegistrationPost.class)
                                                      @JsonView(AddressDto.AddressView.RegistrationPost.class) AddressDto addressDto) {
        try {
            Optional<CompanyModel> companyOptional = userCompanyValidationUtil.validateUserAndCompany();
            if (!companyOptional.isPresent()) {
                log.warn("User not authenticated, not found or company not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated, not found or company not found");
            }
            if (addressDto.getAddressId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The addressId field must be null");
            }
            var addressModel = new AddressModel();
            BeanUtils.copyProperties(addressDto, addressModel);
            addressModel.setType(AddressType.COMPANY);
            addressModel.setCompany(companyOptional.get());
            addressService.save(addressModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(addressModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
   }
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/update-address/{addressId}")
    public ResponseEntity<Object> updateAddress(@PathVariable UUID addressId,
                                                @RequestBody
                                                @Validated(AddressDto.AddressView.RegistrationPost.class)
                                                @JsonView(AddressDto.AddressView.RegistrationPost.class) AddressDto addressDto,
                                                Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<AddressModel> addressModelOptional = addressService.findById(addressId);
            if (addressModelOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
            }
            var addressModel = new AddressModel();
            BeanUtils.copyProperties(addressDto, addressModel);
            addressService.save(addressModel);
            return ResponseEntity.ok(addressModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    } //TODO: Implementar a validação de usuário
}