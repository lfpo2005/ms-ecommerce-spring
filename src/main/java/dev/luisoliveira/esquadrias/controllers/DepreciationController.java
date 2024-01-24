package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.DepreciationDto;
import dev.luisoliveira.esquadrias.models.DepreciationModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.DepreciationService;
import dev.luisoliveira.esquadrias.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/depreciation")
public class DepreciationController {

    @Autowired
    DepreciationService depreciationService;

    @Autowired
    UserService userService;

    @PostMapping("/createDepreciation")
    public ResponseEntity<Object> registerDepreciation(Authentication authentication,
                                                       @RequestBody @Validated(DepreciationDto.DepreciationView.RegistrationPost.class)
                                                       @JsonView(DepreciationDto.DepreciationView.RegistrationPost.class) DepreciationDto depreciationDto) {

        try {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UUID userId = userDetails.getUserId();
        //Optional<UserModel> currentUser = userService.findById(userDetails.getUserId());

        log.info("Authentication {} ", userDetails.getUsername());


            if (depreciationService.existsByEquipmentAndUserId(depreciationDto.getEquipment(), userId)) {
                log.warn("Depreciation {} is already Taken!: ------> ", depreciationDto.getDepreciationId());
                return ResponseEntity.badRequest().body("Error: Depreciation is Already Taken!");
            }
                var depreciation = new DepreciationModel();
                BeanUtils.copyProperties(depreciationDto, depreciation);
                depreciation.setUser(userId);
                depreciationService.save(depreciation);
                return ResponseEntity.status(HttpStatus.CREATED).body(depreciation);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAllDepreciation(Authentication authentication) {
        try {
            // Extrai o ID do usuário logado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();

            // Chama o serviço passando o ID do usuário
            List<DepreciationModel> depreciations = depreciationService.findAllByUserId(userId);
            return ResponseEntity.ok(depreciations);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

}
