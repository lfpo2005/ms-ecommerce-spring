package dev.msusermanagement.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.msusermanagement.configurations.kafka.UserProducer;
import dev.msusermanagement.configurations.security.JwtProvider;
import dev.msusermanagement.dtos.JwtDto;
import dev.msusermanagement.dtos.LoginDto;
import dev.msusermanagement.dtos.UserDto;
import dev.msusermanagement.enums.RoleType;
import dev.msusermanagement.enums.UserType;
import dev.msusermanagement.exceptions.*;
import dev.msusermanagement.models.RoleModel;
import dev.msusermanagement.models.UserModel;
import dev.msusermanagement.services.RoleService;
import dev.msusermanagement.services.UserService;
import dev.msusermanagement.utils.CryptoUtils;
import dev.msusermanagement.utils.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserProducer userProducer;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        log.debug("POST registerUser UserDto received: ------> {}", userDto.toString());
        try {
            validateUserRegistration(userDto);
            RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not Found."));
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            var userModel = new UserModel();
            BeanUtils.copyProperties(userDto, userModel);
            userModel.setUserType(UserType.USER);
            userModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
            userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            userModel.getRoles().add(roleModel);

            validatedAndCryptoCPF(userDto, userModel);
            userService.save(userModel);
            userProducer.sendUserDetails(userModel);
            log.debug("POST registerUser userModel saved: ------> {}", userModel.getUserId());
            log.trace("User saved successfully ------> userId: {} ", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    private void validatedAndCryptoCPF(UserDto userDto, UserModel userModel) {
        try {
            String encryptedCpf = CryptoUtils.encrypt(userDto.getCpf());
            if (userService.existsByCpf(encryptedCpf)) {
                throw new CpfAlreadyTakenException();
            }
            userModel.setCpf(encryptedCpf);
        } catch (CpfAlreadyTakenException e) {
            log.warn("CPF duplicated ---------------: {}", userDto.getCpf());
            throw e;
        } catch (Exception e) {
            log.error("Error encrypting CPF", e);
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encrypting CPF");
        }
    }

    private void validateUserRegistration(UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername())) {
            throw new UsernameAlreadyTakenException();
        }
        if (userService.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyTakenException();
        }
        if (userService.existsByFullName(userDto.getFullName())) {
            throw new FullNameAlreadyTakenException();
        }
        if (!DateUtil.isValidBirthDate(userDto.getBirthDate())) {
            throw new InvalidBirthDateFormatException();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwt(authentication);
            return ResponseEntity.ok(new JwtDto(jwt));
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
            throw e;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public String index() {
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        return "Logging Spring Boot...";
    }
}
