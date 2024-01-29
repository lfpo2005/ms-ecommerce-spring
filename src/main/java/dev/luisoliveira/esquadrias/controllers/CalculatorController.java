package dev.luisoliveira.esquadrias.controllers;

import dev.luisoliveira.esquadrias.models.SumAllValues;
import dev.luisoliveira.esquadrias.services.CalculatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/calculator")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sumAllValues")
    public ResponseEntity<SumAllValues> getFinancialsumAllValues() {


        SumAllValues sumAllServices = calculatorService.sumAllServices();
        SumAllValues sumAllTaxes = calculatorService.sumFindAllTaxes();
        SumAllValues calculatedTotalMonthly = calculatorService.totalMonthly();

        SumAllValues sumAllValues = new SumAllValues();
        sumAllValues.setTotalDepreciation(sumAllServices.getTotalDepreciation());
        sumAllValues.setTotalFixedCosts(sumAllServices.getTotalFixedCosts());
        sumAllValues.setTotalVariableCosts(sumAllServices.getTotalVariableCosts());
        sumAllValues.setTotalEmployeeCosts(sumAllServices.getTotalEmployeeCosts());
        sumAllValues.setTotalTaxes(sumAllTaxes.getTotalTaxes());
        sumAllValues.setTotalProfit(sumAllTaxes.getTotalProfit());
        sumAllValues.setTotalCommission(sumAllTaxes.getTotalCommission());

        sumAllValues.setTotalSumTaxesCommissionProfit(sumAllTaxes.getTotalSumTaxesCommissionProfit());
        sumAllValues.setTotalSumServices(sumAllServices.getTotalSumServices());
        sumAllValues.setTotalMonthly(calculatedTotalMonthly.getTotalMonthly());

        return ResponseEntity.ok(sumAllValues);
    }



}
