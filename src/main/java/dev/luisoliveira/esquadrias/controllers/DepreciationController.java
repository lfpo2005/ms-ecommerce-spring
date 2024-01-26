package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.DepreciationDto;
import dev.luisoliveira.esquadrias.models.DepreciationModel;
import dev.luisoliveira.esquadrias.services.DepreciationService;
import dev.luisoliveira.esquadrias.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
                                                       @RequestBody @Validated(DepreciationDto.DepreciationView.DepreciationPost.class)
                                                       @JsonView(DepreciationDto.DepreciationView.DepreciationPost.class) DepreciationDto depreciationDto) {

        try {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UUID userId = userDetails.getUserId();

        log.info("Authentication {} ", userDetails.getUsername());


            if (depreciationService.existsByEquipmentAndUserId(depreciationDto.getEquipment(), userId)) {
                log.warn("Depreciation {} is already Taken!: ------> ", depreciationDto.getDepreciationId());
                return ResponseEntity.badRequest().body("Error: Depreciation is Already Taken!");
            }
                var depreciation = new DepreciationModel();
                BeanUtils.copyProperties(depreciationDto, depreciation);
                depreciation.setUser(userId);
                depreciationService.save(depreciation);
                log.info("POST registerDepreciation DepreciationDto received: ------> {}", depreciationDto.toString());
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
            log.info("Authentication {} ", userDetails.getUsername());

            // Chama o serviço passando o ID do usuário
            List<DepreciationModel> depreciation = depreciationService.findAllByUserId(userId);
            log.info("Depreciation {} ", depreciation);
            return ResponseEntity.ok(depreciation);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{depreciationId}/getOneDepreciation")
    public ResponseEntity<Object> getOneDepreciation(@PathVariable("depreciationId") UUID depreciationId,
                                                     Authentication authentication) {

        try {
            // Extrai o ID do usuário logado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            // Busca a depreciação pelo ID
            Optional<DepreciationModel> depreciationOptional = depreciationService.findById(depreciationId);

            if (depreciationOptional.isPresent()) {
                DepreciationModel depreciation = depreciationOptional.get();

                // Verifica se a depreciação pertence ao usuário logado
                if (depreciation.getUser().getUserId().equals(userId)) {
                    return ResponseEntity.ok(depreciation);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depreciation not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/{depreciationId}/updateDepreciation")
    public ResponseEntity<Object> updateDepreciation(@PathVariable UUID depreciationId,
                                                     @RequestBody
                                                     @Validated(DepreciationDto.DepreciationView.DepreciationPut.class)
                                                     @JsonView(DepreciationDto.DepreciationView.DepreciationPut.class)
                                                     DepreciationDto depreciationDto, Authentication authentication ) {
        try {
            // Extrai o ID do usuário logado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            // Busca a depreciação pelo ID
            Optional<DepreciationModel> depreciationOptional = depreciationService.findById(depreciationId);

            if (depreciationOptional.isPresent()) {
                DepreciationModel depreciation = depreciationOptional.get();

                // Verifica se a depreciação pertence ao usuário logado
                if (depreciation.getUser().getUserId().equals(userId)) {
                    var depreciationModel = depreciationOptional.get();
                    depreciationModel.setEquipment(depreciationDto.getEquipment());
                    depreciationModel.setEquipment(depreciationDto.getEquipment());
                    depreciationModel.setQuantityEquipment(depreciationDto.getQuantityEquipment());
                    depreciationModel.setPriceEquipment(depreciationDto.getPriceEquipment());
                    return ResponseEntity.ok(depreciationService.save(depreciationModel));

                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depreciation not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
