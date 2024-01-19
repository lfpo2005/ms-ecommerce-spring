package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;

import dev.luisoliveira.esquadrias.configs.security.AuthenticationCurrentUserService;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.UserDto;
import dev.luisoliveira.esquadrias.models.AddressModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.UserService;
import dev.luisoliveira.esquadrias.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationCurrentUserService authenticationCurrentUserService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@RequestParam(required = false) Boolean isActive,
                                                       @RequestParam(required = false) Boolean isDeleted,
                                                       @RequestParam(required = false) String email,
                                                       @RequestParam(required = false) String fullName,
                                                       SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                       Authentication authentication) {

        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("Authentication {} ", userDetails.getUsername());
        
        // Filtros para buscar usuarios
        final Specification<UserModel> combinedSpec = getUserModelSpecification(isActive, isDeleted, email, fullName, spec);

        Page<UserModel> userModelPage = userService.findAll(combinedSpec, pageable);
        if (!userModelPage.isEmpty()) {
            for (UserModel user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    private static Specification<UserModel> getUserModelSpecification(Boolean isActive, Boolean isDeleted, String email, String fullName, SpecificationTemplate.UserSpec spec) {
        Specification<UserModel> combinedSpec = Specification.where(spec);

        if (isActive != null) {
            combinedSpec = combinedSpec.and(SpecificationTemplate.UserSpec.isActive(isActive));
        }

        if (isDeleted != null) {
            combinedSpec = combinedSpec.and(SpecificationTemplate.UserSpec.isDeleted(isDeleted));
        }
        if (email != null) {
            combinedSpec = combinedSpec.and(SpecificationTemplate.UserSpec.email(email));
        }
        if (fullName != null) {
            combinedSpec = combinedSpec.and(SpecificationTemplate.UserSpec.fullName(fullName));
        }
        return combinedSpec;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if (currentUserId.equals(userId)) {
            Optional<UserModel> userModelOptional = userService.findById(userId);
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            } else {
                UserModel user = userModelOptional.get();

                Set<AddressModel> addresses = user.getAddress();
                Set<PhoneModel> phones = user.getPhones();

                Map<String, Object> response = new HashMap<>();
                response.put("user", user);
                response.put("addresses", addresses);
                response.put("phones", phones);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{userId}/deactivate-delete-user")
    public ResponseEntity<Object> deactivateAndDeleteUser(@PathVariable(value = "userId") UUID userId) {

        log.debug("PUT deactivateUser UserDto received: ------> {}", userId.toString());

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isPresent() && userModelOptional.get().isDeleted() == true) {
            log.debug("User not found, deactivate user ------> userId: {} ", userModelOptional.get().getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or already deleted");
        } else {
            var userModel = userModelOptional.get();
            userModel.setActive(false);
            userModel.setDeleted(true);
            userModel.setDeleteAt(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);
            log.debug("PATH deactivateUser userModel : ------> userId: {}", userModel.getUserId());
            log.info("User deactivate successfully ------> userId: {} ", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{userId}/deactivate-user")
    public ResponseEntity<Object> deactivateUser(@PathVariable(value = "userId") UUID userId) {

        log.debug("PUT deactivateUser UserDto received: ------> {}", userId.toString());

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isPresent() && userModelOptional.get().isActive() == false) {
            log.debug("User not found or is disabled user ------> userId: {} ", userModelOptional.get().getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or already disabled");
        } else {
            var userModel = userModelOptional.get();
            userModel.setActive(false);
            userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);
            log.debug("PATH deactivateUser userModel : ------> userId: {}", userModel.getUserId());
            log.info("User deactivate successfully ------> userId: {} ", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{userId}/activate-user")
    public ResponseEntity<Object> activateUser(@PathVariable(value = "userId") UUID userId) {

        log.debug("PUT deactivateUser UserDto received: ------> {}", userId.toString());

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isPresent() && userModelOptional.get().isActive() == true) {
            log.debug("User not found or is active -----> userId: {} ", userModelOptional.get().getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or already active");
        } else {
            var userModel = userModelOptional.get();
            userModel.setActive(true);
            userModel.setDeleted(false);
            userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);
            log.debug("PATH deactivateUser userModel : ------> userId: {}", userModel.getUserId());
            log.info("User deactivate successfully ------> userId: {} ", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {

        log.debug("PUT updateUser UserDto received: ------> {}", userDto.toString());

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isPresent() && userModelOptional.get().isDeleted() == false) {
            var userModel = userModelOptional.get();
            userModel.setFullName(userDto.getFullName());
            userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);
            log.debug("PUT updateUser userModel : ------> userId: {}", userModel.getUserId());
            log.info("User updated successfully ------> userId: {} ", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {

        log.debug("Request to update password for userId: {}", userId);
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isPresent() && !userModelOptional.get().isDeleted()) {
            var userModel = userModelOptional.get();
            if (!passwordEncoder.matches(userDto.getOldPassword(), userModel.getPassword())) {
                log.debug("Mismatched old password for userId: {}", userId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Mismatched old password!");
            } else {
                userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
                userService.updatePassword(userModel);
                log.info("Password updated successfully for userId: {}", userId);
                return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
            }
        } else {
            log.debug("User not found or deleted for userId: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PatchMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        log.debug("PUT updateImage UserDto received: ------> {}", userDto.toString());

        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            var userModel = userModelOptional.get();
            userModel.setImageUrl(userDto.getImageUrl());
            userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);
            log.debug("PUT updateUser userModel : ------> userId: {}", userModel.getUserId());
            log.info("User updated Image successfully ------> userId: {} ", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
}
