package dev.calculator.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.calculator.configs.security.UserDetailsImpl;
import dev.calculator.dtos.VariableCostDto;
import dev.calculator.services.UserService;
import dev.calculator.services.VariableCostService;

import dev.calculator.models.VariableCostModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/variableCosts")
public class VariableCostController {

    @Autowired
    VariableCostService variableCostService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/register-variable-cost")
    public ResponseEntity<Object> registerVariableCost(Authentication authentication,
                                                       @RequestBody @Validated(VariableCostDto.VariableCostView.VariableCostPost.class)
                                                       @JsonView(VariableCostDto.VariableCostView.VariableCostPost.class) VariableCostDto variableCostDto) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            if (variableCostService.existsByNameCostsAndUser_UserId(variableCostDto.getNameCosts(), userId)) {
                log.warn("VariableCost {} is already Taken!: ------> ", variableCostDto.getVariableCostId());
                return ResponseEntity.badRequest().body("Error: VariableCost is Already Taken!");
            }
            var variableCostModel = new VariableCostModel();
            BeanUtils.copyProperties(variableCostDto, variableCostModel);
            variableCostModel.setUser(userId);
            variableCostService.save(variableCostModel);
            log.info("POST registerVariableCost VariableCostDto received: ------> {}", variableCostDto.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(variableCostModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Object> getAllVariableCost(Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            List<VariableCostModel> variableCostModel = variableCostService.findAllByUserId(userId);
            log.info("Get gelAllVariableCosts VariableCostDto received: ------> {}", variableCostModel.toString());
            return ResponseEntity.status(HttpStatus.OK).body(variableCostModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{variableCostId}")
    public ResponseEntity<Object> getOneVariableCost(@PathVariable(value = "variableCostId") UUID variableCostId,
                                                  Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<VariableCostModel> variableCostModelOptional = variableCostService.findById(variableCostId);
            if (variableCostModelOptional.isPresent()) {
                VariableCostModel variableCostModel = variableCostModelOptional.get();
                if (variableCostModel.getUser().getUserId().equals(userId)) {
                    log.info("GET getOneVariableCost VariableCost received: ------> {}", variableCostModel.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(variableCostModel);
                } else {
                    log.warn("GET getOneVariableCost VariableCost received: ------> {}", variableCostModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("GET getOneFVariableCost variableCost received: ------> {}", variableCostModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: VariableCost not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{variableCostModelId}")
    public ResponseEntity<Object> updateVariableCost(@PathVariable(value = "variableCostModelId") UUID variableCostModelId,
                                                  @RequestBody @Validated(VariableCostDto.VariableCostView.VariableCostPut.class)
                                                  @JsonView(VariableCostDto.VariableCostView.VariableCostPut.class)VariableCostDto variableCostModelDto,
                                                  Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<VariableCostModel> variableCostModelOptional = variableCostService.findById(variableCostModelId);
            if (variableCostModelOptional.isPresent()) {
                VariableCostModel variableCostModel = variableCostModelOptional.get();
                if (variableCostModel.getUser().getUserId().equals(userId)) {
                    var variableCostModelModel = variableCostModelOptional.get();
                    variableCostModelModel.setNameCosts(variableCostModelDto.getNameCosts());
                    variableCostModelModel.setValueVariableCosts(variableCostModelDto.getValueVariableCosts());
                    variableCostModelModel.setDescription(variableCostModelDto.getDescription());
                    variableCostService.save(variableCostModelModel);
                    log.info("PUT updateVariableCost VariableCost received: ------> {}", variableCostModelModel.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(variableCostModelModel);
                } else {
                    log.warn("PUT updateVariableCost VariableCost received: ------> {}", variableCostModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("PUT updateVariableCost VariableCost received: ------> {}", variableCostModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: VariableCost not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{variableCostModelId}")
    public ResponseEntity<Object> deleteVariableCost(@PathVariable(value = "variableCostModelId") UUID variableCostModelId,
                                                  Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<VariableCostModel> variableCostModelOptional = variableCostService.findById(variableCostModelId);
            if (variableCostModelOptional.isPresent()) {
                VariableCostModel variableCostModel = variableCostModelOptional.get();
                if (variableCostModel.getUser().getUserId().equals(userId)) {
                    variableCostService.delete(variableCostModel);
                    log.info("DELETE deleteVariableCost VariableCost received: ------> {}", variableCostModel.toString());
                    return ResponseEntity.status(HttpStatus.OK).body("VariableCost deleted successfully.");
                } else {
                    log.warn("DELETE deleteVariableCost VariableCost received: ------> {}", variableCostModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("DELETE deleteVariableCost VariableCost received: ------> {}", variableCostModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: VariableCost not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}
