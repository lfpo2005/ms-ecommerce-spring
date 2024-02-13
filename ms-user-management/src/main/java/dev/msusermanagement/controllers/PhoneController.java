package dev.msusermanagement.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.msusermanagement.configurations.security.UserDetailsImpl;
import dev.msusermanagement.dtos.PhoneDto;
import dev.msusermanagement.models.PhoneModel;
import dev.msusermanagement.models.UserModel;
import dev.msusermanagement.services.PhoneService;
import dev.msusermanagement.services.UserService;
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
@RequestMapping("/phones")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @Autowired
    UserService userService;


    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/register-phone")
    public ResponseEntity<Object> registerPhoneForUser(@RequestBody
                                                       @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                                       @JsonView(PhoneDto.PhoneView.RegistrationPost.class)
                                                       PhoneDto phoneDto, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.trace("POST registerPhone PhoneDto received: ------> {}", phoneDto.toString());
        Optional<UserModel> userModelOptional = userService.findById(userDetails.getUserId());

        try {
            if (!userModelOptional.isPresent()) {
                log.trace("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if (phoneDto.getPhoneId() != null) {
                log.trace("The phoneId field must be null");
                return ResponseEntity.status(HttpStatus.CONFLICT).body("The phoneId field must be null");
            }
            var phoneModel = new PhoneModel();
            BeanUtils.copyProperties(phoneDto, phoneModel);
            phoneModel.setUserPhone(userModelOptional.get());
            log.trace("POST registerPhone PhoneModel created: ------> {}", phoneModel.toString());
            phoneService.save(phoneModel);
            log.trace("POST registerPhone PhoneModel saved: ------> {}", phoneModel.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body("Phone registered successfully");

        } catch (Exception e) {
            log.trace("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("user/update-phone/{phoneId}")
    public ResponseEntity<Object> updatePhone(@PathVariable UUID phoneId,
                                              @RequestBody
                                              @Validated(PhoneDto.PhoneView.RegistrationPost.class)
                                              @JsonView(PhoneDto.PhoneView.RegistrationPost.class)
                                              PhoneDto phoneDto) {

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