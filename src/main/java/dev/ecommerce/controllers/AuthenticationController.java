package dev.ecommerce.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ecommerce.configs.security.JwtProvider;
import dev.ecommerce.dtos.JwtDto;
import dev.ecommerce.dtos.LoginDto;
import dev.ecommerce.dtos.UserDto;
import dev.ecommerce.enums.RoleType;
import dev.ecommerce.enums.UserType;
import dev.ecommerce.services.RoleService;
import dev.ecommerce.services.UserService;
import dev.ecommerce.models.RoleModel;
import dev.ecommerce.models.UserModel;
import dev.ecommerce.utils.CryptoUtils;
import dev.ecommerce.utils.DateUtil;
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

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {

        log.debug("POST registerUser UserDto received: ------> {}", userDto.toString());

        try {
            if (userService.existsByUsername(userDto.getUsername())) {
                log.warn("Username {} is Already Taken!: ------> ", userDto.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
            }
            if (userService.existsByEmail(userDto.getEmail())) {
                log.warn("Email {} is Already Taken!: ------> ", userDto.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
            }
            if (userService.existsByFullName(userDto.getFullName())) {
                log.warn("FullName {} is Already Taken!: ------> ", userDto.getFullName());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: FullName is Already Taken!");
            }

            if (!DateUtil.isValidBirthDate(userDto.getBirthDate())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid BirthDate format! Valid format: dd-MM-yyyy");
            }

            RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not Found."));
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            var userModel = new UserModel();
            BeanUtils.copyProperties(userDto, userModel);
            userModel.setUserType(UserType.USER);
            userModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
            userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            userModel.getRoles().add(roleModel);

            try {
                String encryptedCpf = CryptoUtils.encrypt(userDto.getCpf());
                userModel.setCpf(encryptedCpf);
            } catch (Exception e) {
                log.error("Error encrypting CPF", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encrypting CPF");
            }

            userService.save(userModel);
            log.debug("POST registerUser userModel saved: ------> {}", userModel.getUserId());
            log.trace("User saved successfully ------> userId: {} ", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(userModel);

        }catch (Exception e) {
            log.error("Exception occurred: ", e);
            throw e;
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
    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public String index(){
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        return "Logging Spring Boot...";
    }

}
