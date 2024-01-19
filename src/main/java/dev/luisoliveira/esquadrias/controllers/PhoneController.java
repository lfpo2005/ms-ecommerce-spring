package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.dtos.PhoneDto;
import dev.luisoliveira.esquadrias.enums.PhoneType;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.CompanyService;
import dev.luisoliveira.esquadrias.services.PhoneService;
import dev.luisoliveira.esquadrias.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/{userId}/createPhone")
    public ResponseEntity<Object> registerPhoneForUser(@PathVariable(value = "userId") UUID userId,
                                                  @RequestBody @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                                  @JsonView(PhoneDto.PhoneView.RegistrationPost.class) PhoneDto phoneDto) {

        log.debug("POST registerPhone PhoneDto received: ------> {}", phoneDto.toString());
        Optional<UserModel> userModelOptional = userService.findById(userId);

        try {
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (phoneDto.getPhoneId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The phoneId field must be null");
            }
            var phoneModel = new PhoneModel();
            BeanUtils.copyProperties(phoneDto, phoneModel);
            phoneModel.setUser(userModelOptional.get());
            phoneService.save(phoneModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(phoneModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/companies/{companyId}/createPhone")
    public ResponseEntity<Object> registerPhoneForCompany(@PathVariable(value = "companyId") UUID companyId,
                                                          @RequestBody @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                                          @JsonView(PhoneDto.PhoneView.RegistrationPost.class) PhoneDto phoneDto) {

        log.debug("POST registerPhoneForCompany PhoneDto received: ------> {}", phoneDto.toString());
        Optional<CompanyModel> companyModelOptional = companyService.findById(companyId);

        try {
            if (!companyModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
            }
            if (phoneDto.getPhoneId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The phoneId field must be null");
            }
            var phoneModel = new PhoneModel();
            BeanUtils.copyProperties(phoneDto, phoneModel);
            phoneModel.setCompany(companyModelOptional.get());
            phoneService.save(phoneModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(phoneModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

//    @PreAuthorize("hasAnyRole('USER')")
//    @GetMapping("/{phoneId}")
//    public ResponseEntity<Object> getPhone(@PathVariable UUID phoneId) {
//        try {
//            Optional<PhoneModel> phoneModelOptional = phoneService.findById(phoneId);
//            if (phoneModelOptional == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone not found");
//            }
//            return ResponseEntity.ok(phoneModelOptional);
//        } catch (Exception e) {
//            log.error("Specific error occurred", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
//        }
//    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{userId}/updatePhone/{phoneId}")
    public ResponseEntity<Object> updatePhone(@PathVariable UUID phoneId,
                                                @RequestBody
                                                @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                                @JsonView(PhoneDto.PhoneView.RegistrationPost.class) PhoneDto phoneDto) {
        try {
            Optional<PhoneModel> phoneModelOptional = phoneService.findById(phoneId);
            if (phoneModelOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone not found");
            }
            var phoneModel = new PhoneModel();
            BeanUtils.copyProperties(phoneDto, phoneModel);
            phoneService.save(phoneModel);
            return ResponseEntity.ok(phoneModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{phoneId}/deactivate-phone")
    public ResponseEntity<Object> deactivatePhone(@PathVariable UUID phoneId) {
        try {
            Optional<PhoneModel> phoneModelOptional = phoneService.findById(phoneId);
            if (phoneModelOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone not found");
            }

            var phoneModel = new PhoneModel();
            phoneModel.setActive(false);
            phoneModel.setDeleted(true);
            phoneService.save(phoneModel);
            return ResponseEntity.ok("Phone deactivated successfully");
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }
}