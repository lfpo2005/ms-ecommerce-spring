package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.CommissionDto;
import dev.luisoliveira.esquadrias.models.CommissionModel;
import dev.luisoliveira.esquadrias.services.CommissionService;
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
@RequestMapping("/commissions")
public class CommissionController {

    @Autowired
    CommissionService commissionService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/create-commission")
    public ResponseEntity<Object> registerCommission(Authentication authentication,
                                                     @RequestBody @Validated(CommissionDto.CommissionView.CommissionPost.class)
                                                     @JsonView(CommissionDto.CommissionView.CommissionPost.class) CommissionDto commissionDto) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());
            if (commissionService.existsByNameCostsAndUser_UserId(commissionDto.getName(), userId)) {
                log.warn("Commission {} is already Taken!: ------> ", commissionDto.getCommissionId());
                return ResponseEntity.badRequest().body("Error: Commission is Already Taken!");
            }
            var commissionModel = new CommissionModel();
            BeanUtils.copyProperties(commissionDto, commissionModel);
            commissionModel.setUser(userId);
            commissionService.save(commissionModel);
            log.info("POST registerCommission CommissionDto received: ------> {}", commissionDto.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(commissionModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Object> getAllCommission(Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            List<CommissionModel> commissionModels = commissionService.findAllByUserId(userId);
            log.info("GET getAllCommission Commission received: ------> {}", commissionModels.toString());
            return ResponseEntity.status(HttpStatus.OK).body(commissionModels);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{commissionId}")
    public ResponseEntity<Object> getOneCommission(@PathVariable(value = "commissionId") UUID commissionId,
                                                   Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<CommissionModel> commissionModelsOptional = commissionService.findById(commissionId);
            if (commissionModelsOptional.isPresent()) {
                CommissionModel commissionModels = commissionModelsOptional.get();
                if (commissionModels.getUser().getUserId().equals(userId)) {
                    log.info("GET getOneCommission Commission received: ------> {}", commissionModels.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(commissionModels);
                } else {
                    log.warn("GET getOneCommission Commission received: ------> {}", commissionModels.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("GET getOneCommission Commission received: ------> {}", commissionModelsOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Commission not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{commissionId}")
    public ResponseEntity<Object> updateCommission(@PathVariable(value = "commissionId") UUID commissionId,
                                                   @RequestBody @Validated(CommissionDto.CommissionView.CommissionPut.class)
                                                   @JsonView(CommissionDto.CommissionView.CommissionPut.class) CommissionDto fixedCostDto,
                                                   Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<CommissionModel> commissionModelsOptional = commissionService.findById(commissionId);
            if (commissionModelsOptional.isPresent()) {
                CommissionModel commissionModel = commissionModelsOptional.get();
                if (commissionModel.getUser().getUserId().equals(userId)) {
                    var commissionModels = commissionModelsOptional.get();
                    commissionModels.setName(fixedCostDto.getName());
                    commissionModels.setDescription(fixedCostDto.getDescription());
                    commissionModels.setSellerName(fixedCostDto.getSellerName());
                    commissionModels.setValuePercentage(fixedCostDto.getValuePercentage());
                    commissionService.save(commissionModels);
                    log.info("PUT updateCommission Commission received: ------> {}", commissionModels.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(commissionModels);
                } else {
                    log.warn("PUT updateCommission Commission received: ------> {}", commissionModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("PUT updateCommission Commission received: ------> {}", commissionModelsOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Commission not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{commissionId}")
    public ResponseEntity<Object> deleteCommission(@PathVariable(value = "commissionId") UUID commissionId,
                                                   Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<CommissionModel> commissionModelsOptional = commissionService.findById(commissionId);
            if (commissionModelsOptional.isPresent()) {
                CommissionModel fixedCost = commissionModelsOptional.get();
                if (fixedCost.getUser().getUserId().equals(userId)) {
                    commissionService.delete(fixedCost);
                    log.info("DELETE deleteCommission Commission received: ------> {}", fixedCost.toString());
                    return ResponseEntity.status(HttpStatus.OK).body("Commission deleted successfully.");
                } else {
                    log.warn("DELETE deleteCommission Commission received: ------> {}", fixedCost.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("DELETE deleteCommission Commission received: ------> {}", commissionModelsOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Commission not found");
            }
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}