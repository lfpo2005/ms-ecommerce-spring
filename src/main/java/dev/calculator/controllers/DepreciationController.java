package dev.calculator.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.calculator.configs.security.UserDetailsImpl;
import dev.calculator.dtos.DepreciationDto;
import dev.calculator.services.DepreciationService;
import dev.calculator.services.UserService;
import dev.calculator.models.DepreciationModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
@RequestMapping("/depreciation")
public class DepreciationController {

    @Autowired
    DepreciationService depreciationService;

    @Autowired
    UserService userService;

    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/register-depreciation")
    public ResponseEntity<Object> registerDepreciation(@RequestBody
                                                       @Validated(DepreciationDto.DepreciationView.DepreciationPost.class)
                                                       @JsonView(DepreciationDto.DepreciationView.DepreciationPost.class)
                                                       DepreciationDto depreciationDto, Authentication authentication) {

        try {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UUID userId = userDetails.getUserId();
        log.info("Authentication {} ", userDetails.getUsername());
            if (depreciationService.existsByEquipmentAndUserId(depreciationDto.getEquipment(), userId)) {
                log.warn("Depreciation {} is already Taken!: ------> ", depreciationDto.getDepreciationId());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Depreciation is Already Taken!");
            }
                var depreciationModel = new DepreciationModel();
                BeanUtils.copyProperties(depreciationDto, depreciationModel);
                depreciationModel.setUser(userId);
                depreciationService.save(depreciationModel);
                log.info("POST registerDepreciation DepreciationDto received: ------> {}", depreciationDto.toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(depreciationModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Object> findAllDepreciation(Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());
            List<DepreciationModel> depreciation = depreciationService.findAllByUserId(userId);
            log.info("Depreciation {} ", depreciation);
            return ResponseEntity.ok(depreciation);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @GetMapping("/{depreciationId}")
    public ResponseEntity<Object> getOneDepreciation(@PathVariable("depreciationId") UUID depreciationId,
                                                     Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());
            Optional<DepreciationModel> depreciationOptional = depreciationService.findById(depreciationId);
            if (depreciationOptional.isPresent()) {
                DepreciationModel depreciation = depreciationOptional.get();
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
            throw e;
        }
    }
    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PutMapping("/{depreciationId}/update-depreciation")
    public ResponseEntity<Object> updateDepreciation(@PathVariable("depreciationId") UUID depreciationId,
                                                     @RequestBody
                                                     @Validated(DepreciationDto.DepreciationView.DepreciationPut.class)
                                                     @JsonView(DepreciationDto.DepreciationView.DepreciationPut.class)
                                                     DepreciationDto depreciationDto, Authentication authentication ) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<DepreciationModel> depreciationOptional = depreciationService.findById(depreciationId);
            if (depreciationOptional.isPresent()) {
                DepreciationModel depreciation = depreciationOptional.get();
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
                log.warn("GET getOneDepreciation DepreciationDto received: ------> {}", depreciationOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depreciation not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}
