package dev.luisoliveira.esquadrias.controllers;

import dev.luisoliveira.esquadrias.models.SumAllValues;
import dev.luisoliveira.esquadrias.services.CalculatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/calculator")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @GetMapping("/sum-all-services")
    public BigDecimal sumAllEntities() {
        BigDecimal totalDepreciation = calculatorService.sumAllDepreciationValues();
        BigDecimal totalFixedCosts = calculatorService.sumAllFixedCostsValues();
        BigDecimal totalVariableCosts = calculatorService.sumAllVariableCostsValues();
        BigDecimal totalEmployees = calculatorService.sumAllEmployeesValues();
        return totalDepreciation
                .add(totalFixedCosts)
                .add(totalVariableCosts)
                .add(totalEmployees);

    }


    @GetMapping("/sum-taxes")
    public Integer sumTaxes() {
        Integer total = calculatorService.sumAllTaxesValues();
        return (total);
    }
    @GetMapping("/sum-profit")
    public Integer sumProfit() {
        Integer total = calculatorService.sumAllProfitValues();
        return (total);
    }
    @GetMapping("/sum-commission")
    public Integer sumCommission() {
        Integer total = calculatorService.sumAllCommissionValues();
        return (total);
    }

    @GetMapping("/sumAllValues")
    public ResponseEntity<SumAllValues> getFinancialsumAllValues() {
        SumAllValues sumAllServices = calculatorService.sumAllServices();
        SumAllValues sumAllTaxes = calculatorService.sumAllTaxes();
        SumAllValues calculatedTotalMonthly = calculatorService.totalMonthly();


        SumAllValues sumAllValues = new SumAllValues();
        sumAllValues.setTotalSumTaxes(sumAllTaxes.getTotalSumTaxes());
        sumAllValues.setTotalSumServices(sumAllServices.getTotalSumServices());
        sumAllValues.setTotalMonthly(calculatedTotalMonthly.getTotalMonthly());

        return ResponseEntity.ok(sumAllValues);
    }



}
