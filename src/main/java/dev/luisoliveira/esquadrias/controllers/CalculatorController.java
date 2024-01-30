package dev.luisoliveira.esquadrias.controllers;

import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.exceptions.CalculationException;
import dev.luisoliveira.esquadrias.models.CalculatorSumModel;
import dev.luisoliveira.esquadrias.services.CalculatorService;
import dev.luisoliveira.esquadrias.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/calculator")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;
    @Autowired
    UserService userService;


    @PostMapping
    public void calculateSum(Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            if (calculatorService.existsByUser_UserId(userId)) {
                log.warn("CalculatorSumModel {} is already Taken!: ------> ", userId);
            }
            Optional<CalculatorSumModel> calculatorSumModelOptional = calculatorService.findById(userId);
            var calculatorSumModel = calculatorSumModelOptional.get();
            calculatorSumModel.setUser(userId);

            calculatorService.save(calculatorSumModel);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    //@Cacheable(value = "sumValuesCache", key = "#userId")
    public ResponseEntity<Object> getAllSumAllValues(Authentication authentication) throws CalculationException {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.info("Authentication {} ", userDetails.getUsername());

            CalculatorSumModel calculatorSum = calculatorService.findByUser_UserId(userId);
            log.info("GET getFinancialSumAllValues userId: ------> {}", userId);
            if (calculatorSum == null || calculatorSum.getCalculatorSumId() == null) {
                calculateSum(authentication);
            }
            Optional<CalculatorSumModel> calculatorSumModelOptional = calculatorService.findById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(calculatorSumModelOptional);

        } catch (Exception e) {
            log.error("Specific error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @PutMapping
    public void updateSumValues(Authentication authentication, UUID calculatorSumId) throws CalculationException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UUID userId = userDetails.getUserId();
        log.info("Authentication {} ", userDetails.getUsername());

        Optional<CalculatorSumModel> calculatorSumModelOptional = calculatorService.findById(calculatorSumId);
        calculatorSumModelOptional.get().getCalculatorSumId();
        System.out.println("GetCalculatorSumId():  ---------------------> " + calculatorSumModelOptional.get().getCalculatorSumId());

        if (calculatorService.existsByUser_UserId(userId)) {
            log.warn("CalculatorSumModel {} is already Taken!: ------> ", userId);
        }
        if (calculatorSumModelOptional.isPresent() && calculatorSumModelOptional.get().getCalculatorSumId() != null) {
            CalculatorSumModel calculator = calculatorSumModelOptional.get();
            if (calculator.getUser().getUserId().equals(userId)) {
                saveSum(calculatorSumModelOptional);
            } else {
                log.warn("CalculatorSumModel {} is already Taken!: ------> ", userId);
            }
        } else {
            calculateSum(authentication);
        }
    }

    private void saveSum(Optional<CalculatorSumModel> calculatorSumModelOptional) throws CalculationException {
        var calculatorSumModel = calculatorSumModelOptional.get();
        CalculatorSumModel calculatedTotalMonthly = calculatorService.totalMonthly(calculatorSumModel.getUser().getUserId());
        calculatorSumModel.setTotalMonthly(calculatedTotalMonthly.getTotalMonthly());
        calculatorSumModel.setTotalDepreciation(calculatedTotalMonthly.getTotalDepreciation());
        calculatorSumModel.setTotalFixedCosts(calculatedTotalMonthly.getTotalFixedCosts());
        calculatorSumModel.setTotalVariableCosts(calculatedTotalMonthly.getTotalVariableCosts());
        calculatorSumModel.setTotalEmployeeCosts(calculatedTotalMonthly.getTotalEmployeeCosts());
        calculatorSumModel.setTotalTaxes(calculatedTotalMonthly.getTotalTaxes());
        calculatorSumModel.setTotalProfit(calculatedTotalMonthly.getTotalProfit());
        calculatorSumModel.setTotalCommission(calculatedTotalMonthly.getTotalCommission());
        calculatorSumModel.setTotalSumTaxesCommissionProfit(calculatedTotalMonthly.getTotalSumTaxesCommissionProfit());
        calculatorSumModel.setTotalSumServices(calculatedTotalMonthly.getTotalSumServices());
        calculatorService.save(calculatorSumModel);
    }
}