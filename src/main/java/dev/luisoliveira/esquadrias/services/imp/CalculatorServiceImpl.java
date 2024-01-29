package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.SumAllValues;
import dev.luisoliveira.esquadrias.repositories.*;
import dev.luisoliveira.esquadrias.services.CalculatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
public class CalculatorServiceImpl implements CalculatorService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TaxesRepository taxesRepository;
    @Autowired
    ProfitRepository profitRepository;
    @Autowired
    CommissionRepository commissionRepository;
    @Autowired
    private DepreciationRepository depreciationRepository;
    @Autowired
    private FixedCostRepository fixedCostRepository;
    @Autowired
    private VariableCostRepository variableCostRepository;

    @Override
    public BigDecimal sumAllDepreciationValues() {
        return depreciationRepository.priceEquipment();
    }

    @Override
    public BigDecimal sumAllFixedCostsValues() {
        return fixedCostRepository.valueFixedCosts();
    }

    @Override
    public BigDecimal sumAllVariableCostsValues() {
        return variableCostRepository.valueVariableCosts();
    }

    @Override
    public BigDecimal sumAllEmployeesValues() {
        return employeeRepository.valueEmployees();
    }

    @Override
    public Integer sumAllTaxesValues() {
        return taxesRepository.sumAllTaxesValues();
    }

    @Override
    public Integer sumAllProfitValues() {
        return profitRepository.sumAllProfitValues();
    }

    @Override
    public Integer sumAllCommissionValues() {
        return commissionRepository.sumAllCommissionValues();
    }


    @Override
    public SumAllValues sumAllServices() {
        SumAllValues sumAllValues = new SumAllValues();

        sumAllValues.setTotalDepreciation(depreciationRepository.priceEquipment());
        sumAllValues.setTotalFixedCosts(fixedCostRepository.valueFixedCosts());
        sumAllValues.setTotalVariableCosts(variableCostRepository.valueVariableCosts());
        sumAllValues.setTotalEmployeeCosts(employeeRepository.valueEmployees());


        BigDecimal totalSumServices = sumAllValues.getTotalDepreciation()
                .add(sumAllValues.getTotalFixedCosts())
                .add(sumAllValues.getTotalVariableCosts())
                .add(sumAllValues.getTotalEmployeeCosts());
        sumAllValues.setTotalSumServices(totalSumServices);

        return sumAllValues;
    }

    @Override
    public SumAllValues sumAllTaxes() {
        SumAllValues sumAllValues = new SumAllValues();
        sumAllValues.setTotalTaxes(taxesRepository.sumAllTaxesValues());
        sumAllValues.setTotalProfit(profitRepository.sumAllProfitValues());
        sumAllValues.setTotalCommission(commissionRepository.sumAllCommissionValues());

        Integer totalSumTaxes = sumAllValues.getTotalTaxes()
                + sumAllValues.getTotalProfit()
                + sumAllValues.getTotalCommission();
        sumAllValues.setTotalSumTaxes(totalSumTaxes);

        return sumAllValues;
    }

    public SumAllValues totalMonthly() {
        SumAllValues sumAllTaxes = sumAllTaxes();
        SumAllValues sumAllServices = sumAllServices();

        // Converte o valor inteiro de porcentagem para um formato decimal
        BigDecimal porcentagem = new BigDecimal(sumAllTaxes.getTotalSumTaxes()).divide(new BigDecimal(100));

        log.info("Porcentagem: " + porcentagem);
        // Calcula o valor adicional com base na porcentagem
        BigDecimal valorAdicional = sumAllServices.getTotalSumServices().multiply(porcentagem);

        // Calcula o totalMonthly como a soma de totalSumServices e o valor adicional
        BigDecimal totalMonthly = sumAllServices.getTotalSumServices().add(valorAdicional);

        SumAllValues sumAllValues = new SumAllValues();


        sumAllValues.setTotalSumTaxes(sumAllTaxes.getTotalSumTaxes());
        sumAllValues.setTotalSumServices(sumAllServices.getTotalSumServices());
        sumAllValues.setTotalMonthly(totalMonthly);


        return sumAllValues;
    }


}
