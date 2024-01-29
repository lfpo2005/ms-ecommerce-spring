package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.dtos.ProfitDto;
import dev.luisoliveira.esquadrias.models.ProfitModel;
import dev.luisoliveira.esquadrias.services.ProfitService;
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
@RequestMapping("/profit")
public class ProfitController {

    @Autowired
    ProfitService profitService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/createProfit")
    public ResponseEntity<Object> registerProfit(Authentication authentication,
                                                     @RequestBody @Validated(ProfitDto.ProfitView.ProfitPost.class)
                                                     @JsonView(ProfitDto.ProfitView.ProfitPost.class) ProfitDto profitDto) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            if (profitService.existsByNameCostsAndUser_UserId(profitDto.getName(), userId)) {
                log.warn("Profit {} is already Taken!: ------> ", profitDto.getProfitId());
                return ResponseEntity.badRequest().body("Error: Profit is Already Taken!");
            }
            var profitModel = new ProfitModel();
            BeanUtils.copyProperties(profitDto, profitModel);
            profitModel.setUser(userId);
            profitService.save(profitModel);
            log.info("POST registerProfit ProfitDto received: ------> {}", profitDto.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(profitModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Object> getAllProfit(Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            List<ProfitModel> profitModels = profitService.findAllByUserId(userId);
            log.info("GET getAllProfit Profit received: ------> {}", profitModels.toString());
            return ResponseEntity.status(HttpStatus.OK).body(profitModels);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{profitId}")
    public ResponseEntity<Object> getOneProfit(@PathVariable(value = "profitId") UUID profitId,
                                                   Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<ProfitModel> profitModelsOptional = profitService.findById(profitId);

            if (profitModelsOptional.isPresent()) {
                ProfitModel profitModels = profitModelsOptional.get();
                if (profitModels.getUser().getUserId().equals(userId)) {
                    log.info("GET getOneProfit Profit received: ------> {}", profitModels.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(profitModels);
                } else {
                    log.warn("GET getOneProfit Profit received: ------> {}", profitModels.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("GET getOneProfit Profit received: ------> {}", profitModelsOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Profit not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{profitId}")
    public ResponseEntity<Object> updateProfit(@PathVariable(value = "profitId") UUID profitId,
                                                   @RequestBody @Validated(ProfitDto.ProfitView.ProfitPut.class)
                                                   @JsonView(ProfitDto.ProfitView.ProfitPut.class) ProfitDto fixedCostDto,
                                                   Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<ProfitModel> profitModelsOptional = profitService.findById(profitId);

            if (profitModelsOptional.isPresent()) {
                ProfitModel profitModel = profitModelsOptional.get();
                if (profitModel.getUser().getUserId().equals(userId)) {
                    var profitModels = profitModelsOptional.get();
                    profitModels.setName(fixedCostDto.getName());
                    profitModels.setDescription(fixedCostDto.getDescription());
                    profitModels.setValuePercentage(fixedCostDto.getValuePercentage());
                    profitService.save(profitModels);
                    log.info("PUT updateProfit Profit received: ------> {}", profitModels.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(profitModels);
                } else {
                    log.warn("PUT updateProfit Profit received: ------> {}", profitModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("PUT updateProfit Profit received: ------> {}", profitModelsOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Profit not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{profitId}/deleteProfit")
    public ResponseEntity<Object> deleteProfit(@PathVariable(value = "profitId") UUID profitId,
                                                   Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<ProfitModel> profitModelsOptional = profitService.findById(profitId);

            if (profitModelsOptional.isPresent()) {
                ProfitModel fixedCost = profitModelsOptional.get();
                if (fixedCost.getUser().getUserId().equals(userId)) {
                    profitService.delete(fixedCost);
                    log.info("DELETE deleteProfit Profit received: ------> {}", fixedCost.toString());
                    return ResponseEntity.status(HttpStatus.OK).body("Profit deleted successfully.");
                } else {
                    log.warn("DELETE deleteProfit Profit received: ------> {}", fixedCost.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("DELETE deleteProfit Profit received: ------> {}", profitModelsOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Profit not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}