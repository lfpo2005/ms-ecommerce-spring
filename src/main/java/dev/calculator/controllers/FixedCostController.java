package dev.calculator.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.calculator.configs.security.UserDetailsImpl;
import dev.calculator.dtos.FixedCostDto;
import dev.calculator.services.FixedCostService;
import dev.calculator.services.UserService;

import dev.calculator.models.FixedCostModel;
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
@RequestMapping("/fixedCosts")
public class FixedCostController {

    @Autowired
    FixedCostService fixedCostService;

    @Autowired
    UserService userService;

    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/register-fixed-cost")
    public ResponseEntity<Object> registerFixedCost(@RequestBody
                                                    @Validated(FixedCostDto.FixedCostView.FixedCostPost.class)
                                                    @JsonView(FixedCostDto.FixedCostView.FixedCostPost.class)
                                                    FixedCostDto fixedCostDto, Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());
            if (fixedCostService.existsByNameCostsAndUser_UserId(fixedCostDto.getNameCosts(), userId)) {
                log.warn("FixedCost {} is already Taken!: ------> ", fixedCostDto.getFixedCostId());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: FixedCost is Already Taken!");
            }
            var fixedCostModel = new FixedCostModel();
            BeanUtils.copyProperties(fixedCostDto, fixedCostModel);
            fixedCostModel.setUser(userId);
            fixedCostService.save(fixedCostModel);
            log.info("POST registerFixedCost FixedCostDto received: ------> {}", fixedCostDto.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(fixedCostModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Object> getAllFixedCosts(Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());
            List<FixedCostModel> fixedCostModel = fixedCostService.findAllByUserId(userId);
            log.info("GET getAllFixedCosts FixedCostDto received: ------> {}", fixedCostModel.toString());
            return ResponseEntity.status(HttpStatus.OK).body(fixedCostModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{fixedCostId}")
    public ResponseEntity<Object> getOneFixedCost(@PathVariable(value = "fixedCostId")
                                                  UUID fixedCostId, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<FixedCostModel> fixedCostModelOptional = fixedCostService.findById(fixedCostId);
            if (fixedCostModelOptional.isPresent()) {
                FixedCostModel fixedCostModel = fixedCostModelOptional.get();
                if (fixedCostModel.getUser().getUserId().equals(userId)) {
                    log.info("GET getOneFixedCost FixedCostDto received: ------> {}", fixedCostModel.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(fixedCostModel);
                } else {
                    log.warn("GET getOneFixedCost FixedCostDto received: ------> {}", fixedCostModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("GET getOneFixedCost FixedCostDto received: ------> {}", fixedCostModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: FixedCost not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{fixedCostId}")
    public ResponseEntity<Object> updateFixedCost(@PathVariable(value = "fixedCostId") UUID fixedCostId,
                                                  @RequestBody @Validated(FixedCostDto.FixedCostView.FixedCostPut.class)
                                                  @JsonView(FixedCostDto.FixedCostView.FixedCostPut.class)
                                                  FixedCostDto fixedCostDto, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<FixedCostModel> fixedCostModelOptional = fixedCostService.findById(fixedCostId);
            if (fixedCostModelOptional.isPresent()) {
                FixedCostModel fixedCost = fixedCostModelOptional.get();
                if (fixedCost.getUser().getUserId().equals(userId)) {
                    var fixedCostModel = fixedCostModelOptional.get();
                    fixedCostModel.setNameCosts(fixedCostDto.getNameCosts());
                    fixedCostModel.setValueFixedCosts(fixedCostDto.getValueFixedCosts());
                    fixedCostModel.setDescription(fixedCostDto.getDescription());
                    fixedCostService.save(fixedCostModel);
                    log.info("PUT updateFixedCost FixedCostDto received: ------> {}", fixedCostModel.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(fixedCostModel);
                } else {
                    log.warn("PUT updateFixedCost FixedCostDto received: ------> {}", fixedCost.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("PUT updateFixedCost FixedCostDto received: ------> {}", fixedCostModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: FixedCost not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{fixedCostId}")
    public ResponseEntity<Object> deleteFixedCost(@PathVariable(value = "fixedCostId")
                                                  UUID fixedCostId, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<FixedCostModel> fixedCostModelOptional = fixedCostService.findById(fixedCostId);
            if (fixedCostModelOptional.isPresent()) {
                FixedCostModel fixedCost = fixedCostModelOptional.get();
                if (fixedCost.getUser().getUserId().equals(userId)) {
                    fixedCostService.delete(fixedCost);
                    log.info("DELETE deleteFixedCost FixedCostDto received: ------> {}", fixedCost.toString());
                    return ResponseEntity.status(HttpStatus.OK).body("FixedCost deleted successfully.");
                } else {
                    log.warn("DELETE deleteFixedCost FixedCostDto received: ------> {}", fixedCost.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("DELETE deleteFixedCost FixedCostDto received: ------> {}", fixedCostModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: FixedCost not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}