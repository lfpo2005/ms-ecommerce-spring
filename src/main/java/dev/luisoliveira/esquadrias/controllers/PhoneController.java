package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.configs.security.JwtProvider;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.PhoneDto;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.CompanyService;
import dev.luisoliveira.esquadrias.services.PhoneService;
import dev.luisoliveira.esquadrias.services.UserService;
import dev.luisoliveira.esquadrias.utils.UserCompanyValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    UserCompanyValidationUtil userCompanyValidationUtil;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/register-phone")
    public ResponseEntity<Object> registerPhoneForUser(@RequestBody @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                                  @JsonView(PhoneDto.PhoneView.RegistrationPost.class) PhoneDto phoneDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.debug("POST registerPhone PhoneDto received: ------> {}", phoneDto.toString());
        Optional<UserModel> userModelOptional = userService.findById(userDetails.getUserId());

        try {
            if (!userModelOptional.isPresent()) {
                log.error("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (phoneDto.getPhoneId() != null) {
                log.error("The phoneId field must be null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The phoneId field must be null");
            }
            var phoneModel = new PhoneModel();
            BeanUtils.copyProperties(phoneDto, phoneModel);
            phoneModel.setUser(userModelOptional.get());
            phoneService.save(phoneModel);
            log.info("POST registerPhone PhoneModel saved: ------> {}", phoneModel.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(phoneModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/companies/register-phone")
    public ResponseEntity<Object> registerPhoneForCompany(@RequestBody @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                                          @JsonView(PhoneDto.PhoneView.RegistrationPost.class) PhoneDto phoneDto) {
        try {
            Optional<CompanyModel> companyOptional = userCompanyValidationUtil.validateUserAndCompany();
            if (!companyOptional.isPresent()) {
                log.warn("User not authenticated, not found or company not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated, not found or company not found");
            }
            if (phoneDto.getPhoneId() != null) {
                log.error("The phoneId field must be null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The phoneId field must be null");
            }
            var phoneModel = new PhoneModel();
            BeanUtils.copyProperties(phoneDto, phoneModel);
            phoneModel.setCompany(companyOptional.get());
            phoneService.save(phoneModel);
            log.info("POST registerPhone PhoneModel saved: ------> {}", phoneModel.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(phoneModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("user/update-phone/{phoneId}")
    public ResponseEntity<Object> updatePhone(@PathVariable UUID phoneId,
                                                @RequestBody
                                                @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                                @JsonView(PhoneDto.PhoneView.RegistrationPost.class) PhoneDto phoneDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.debug("POST registerPhone PhoneDto received: ------> {}", phoneDto.toString());
        //Optional<UserModel> userModelOptional = userService.findById(userDetails.getUserId());
        try {
            Optional<PhoneModel> phoneModelOptional = phoneService.findById(phoneId);
            if (phoneModelOptional == null) {
                log.error("Phone not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone not found");
            }
            var phoneModel = new PhoneModel();
            BeanUtils.copyProperties(phoneDto, phoneModel);
            phoneService.save(phoneModel);
            log.info("POST registerPhone PhoneModel saved: ------> {}", phoneModel.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(phoneModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}