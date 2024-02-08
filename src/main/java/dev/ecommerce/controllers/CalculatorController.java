package dev.ecommerce.controllers;

import dev.ecommerce.configs.security.UserDetailsImpl;
import dev.ecommerce.exceptions.CalculationException;
import dev.ecommerce.services.CalculatorService;
import dev.ecommerce.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/calculators")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;
    @Autowired
    UserService userService;

    @Cacheable(value = "totalMonthly", key = "#root.args[0].name") // key = "#root.args[0].name" recupera o userId que esta vindo do authentication
    @GetMapping("/sum-all-values")
    public ResponseEntity<Object> getAllSumAllValues(Authentication authentication) throws CalculationException {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());
            log.info("GET getFinancialSumAllValues userId: ------> {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body(calculatorService.totalMonthly(userId));

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}