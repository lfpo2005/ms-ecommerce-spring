package dev.ecommerce.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ecommerce.configs.security.UserDetailsImpl;
import dev.ecommerce.dtos.TaxesDto;
import dev.ecommerce.services.TaxesService;
import dev.ecommerce.models.TaxesModel;
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
@RequestMapping("/taxes")
public class TaxesController {

    @Autowired
    TaxesService taxesService;

    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/register-taxes")
    public ResponseEntity<Object> registerTaxes(@RequestBody
                                                @Validated(TaxesDto.TaxesView.TaxesPost.class)
                                                @JsonView(TaxesDto.TaxesView.TaxesPost.class)
                                                TaxesDto taxesDto, Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            if (taxesService.existsByNameCostsAndUser_UserId(taxesDto.getName(), userId)) {
                log.warn("Taxes {} is already Taken!: ------> ", taxesDto.getTaxesId());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Taxes is Already Taken!");
            }
            var taxesModel = new TaxesModel();
            BeanUtils.copyProperties(taxesDto, taxesModel);
            taxesModel.setUser(userId);
            taxesService.save(taxesModel);
            log.info("POST registerTaxes TaxesDto received: ------> {}", taxesDto.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(taxesModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Object> getAllTaxes(Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            List<TaxesModel> taxesModels = taxesService.findAllByUserId(userId);
            log.info("GET getAllTaxes Taxes received: ------> {}", taxesModels.toString());
            return ResponseEntity.status(HttpStatus.OK).body(taxesModels);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{taxesId}")
    public ResponseEntity<Object> getOneTaxes(@PathVariable(value = "taxesId") UUID taxesId,
                                              Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<TaxesModel> taxesModelOptional = taxesService.findById(taxesId);
            if (taxesModelOptional.isPresent()) {
                TaxesModel taxesModels = taxesModelOptional.get();
                if (taxesModels.getUser().getUserId().equals(userId)) {
                    log.info("GET getOneTaxes Taxes received: ------> {}", taxesModels.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(taxesModels);
                } else {
                    log.warn("GET getOneTaxes Taxes received: ------> {}", taxesModels.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("GET getOneTaxes Taxes received: ------> {}", taxesModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Taxes not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{taxesId}")
    public ResponseEntity<Object> updateTaxes(@PathVariable(value = "taxesId") UUID taxesId,
                                              @RequestBody @Validated(TaxesDto.TaxesView.TaxesPut.class)
                                              @JsonView(TaxesDto.TaxesView.TaxesPut.class)
                                              TaxesDto taxesModelDto, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<TaxesModel> taxesModelOptional = taxesService.findById(taxesId);
            if (taxesModelOptional.isPresent()) {
                TaxesModel taxesModel = taxesModelOptional.get();
                if (taxesModel.getUser().getUserId().equals(userId)) {
                    var taxesModels = taxesModelOptional.get();
                    taxesModels.setName(taxesModelDto.getName());
                    taxesModels.setDescription(taxesModelDto.getDescription());
                    taxesModels.setValuePercentage(taxesModelDto.getValuePercentage());
                    taxesService.save(taxesModels);
                    log.info("PUT updateTaxes Taxes received: ------> {}", taxesModels.toString());
                    return ResponseEntity.status(HttpStatus.OK).body(taxesModels);
                } else {
                    log.warn("PUT updateTaxes Taxes received: ------> {}", taxesModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("PUT updateTaxes Taxes received: ------> {}", taxesModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Taxes not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
    @CacheEvict(value = "totalMonthly", allEntries = true)
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{taxesId}")
    public ResponseEntity<Object> deleteTaxes(@PathVariable(value = "taxesId")
                                              UUID taxesId, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            Optional<TaxesModel> taxesModelOptional = taxesService.findById(taxesId);
            if (taxesModelOptional.isPresent()) {
                TaxesModel taxesModel = taxesModelOptional.get();
                if (taxesModel.getUser().getUserId().equals(userId)) {
                    taxesService.delete(taxesModel);
                    log.info("DELETE deleteTaxes Taxes received: ------> {}", taxesModel.toString());
                    return ResponseEntity.status(HttpStatus.OK).body("Taxes deleted successfully.");
                } else {
                    log.warn("DELETE deleteTaxes Taxes received: ------> {}", taxesModel.toString());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Forbidden");
                }
            } else {
                log.warn("DELETE deleteTaxes Taxes received: ------> {}", taxesModelOptional.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Taxes not found");
            }

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}