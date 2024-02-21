package dev.msusermanagement.controllers;

import dev.msusermanagement.dtos.AdminDto;
import dev.msusermanagement.enums.RoleType;
import dev.msusermanagement.enums.UserType;
import dev.msusermanagement.models.RoleModel;
import dev.msusermanagement.models.UserModel;
import dev.msusermanagement.services.RoleService;
import dev.msusermanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionAdmin(@RequestBody @Valid AdminDto adminDto) {
        Optional<UserModel> userModelOptional = userService.findById(adminDto.getUserId());
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            var userModel = userModelOptional.get();
            userModel.setUserType(UserType.ADMIN);
            userModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
            userModel.getRoles().add(roleModel);
            userService.updateUser(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
}