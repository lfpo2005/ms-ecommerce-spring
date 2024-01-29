package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.*;
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

/*    public BigDecimal sumAllDepreciationValues() {
        return depreciationRepository.priceEquipment();
    }

    public BigDecimal sumAllFixedCostsValues() {
        return fixedCostRepository.valueFixedCosts();
    }

    public BigDecimal sumAllVariableCostsValues() {
        return variableCostRepository.valueVariableCosts();
    }

    public BigDecimal sumAllEmployeesValues() {
        return employeeRepository.valueEmployees();
    }

    public Integer sumAllTaxesValues() {
        return taxesRepository.sumAllTaxesValues();
    }

    public Integer sumAllProfitValues() {
        return profitRepository.sumAllProfitValues();
    }

    public Integer sumAllCommissionValues() {
        return commissionRepository.sumAllCommissionValues();
    }*/


    private DepreciationModel sumAllDepreciation() {
        DepreciationModel depreciationModel = new DepreciationModel();
        depreciationModel.setPriceEquipment(depreciationRepository.priceEquipment());
        return depreciationModel;
    }

    private FixedCostModel sumAllFixedCosts() {
        FixedCostModel fixedCostModel = new FixedCostModel();
        fixedCostModel.setValueFixedCosts(fixedCostRepository.valueFixedCosts());
        return fixedCostModel;
    }

    private VariableCostModel sumAllVariableCosts() {
        VariableCostModel fixedCostModel = new VariableCostModel();
        fixedCostModel.setValueVariableCosts(variableCostRepository.valueVariableCosts());
        return fixedCostModel;
    }

    private CommissionModel sumAllCommission() {
        CommissionModel commissionModel = new CommissionModel();
        commissionModel.setValuePercentage(commissionRepository.sumAllCommissionValues());
        return commissionModel;
    }

    private ProfitModel sumAllProfit() {
        ProfitModel profitModel = new ProfitModel();
        profitModel.setValuePercentage(profitRepository.sumAllProfitValues());
        return profitModel;
    }
    private TaxesModel sumAllTaxes() {
        TaxesModel taxesModel = new TaxesModel();
        taxesModel.setValuePercentage(taxesRepository.sumAllTaxesValues());
        return taxesModel;
    }
    private EmployeeModel sumAllEmployees() {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setSalary(employeeRepository.valueEmployees());
        return employeeModel;
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
    public SumAllValues sumFindAllTaxes() {
        SumAllValues sumAllValues = new SumAllValues();
        sumAllValues.setTotalTaxes(taxesRepository.sumAllTaxesValues());
        sumAllValues.setTotalProfit(profitRepository.sumAllProfitValues());
        sumAllValues.setTotalCommission(commissionRepository.sumAllCommissionValues());

        Integer totalSumTaxes = sumAllValues.getTotalTaxes()
                + sumAllValues.getTotalProfit()
                + sumAllValues.getTotalCommission();
        sumAllValues.setTotalSumTaxesCommissionProfit(totalSumTaxes);

        return sumAllValues;
    }

    public SumAllValues totalMonthly() {
        SumAllValues sumFindAllTaxes = sumFindAllTaxes();
        SumAllValues sumAllServices = sumAllServices();

        // Converte o valor inteiro de porcentagem para um formato decimal
        BigDecimal porcentagem = new BigDecimal(sumFindAllTaxes.getTotalSumTaxesCommissionProfit()).divide(new BigDecimal(100));

        log.info("Porcentagem: " + porcentagem);

        // Calcula o valor adicional com base na porcentagem
        BigDecimal valueMonthly = sumAllServices.getTotalSumServices().multiply(porcentagem);

        // Calcula o amountMonthly como a soma de totalSumServices e o valor adicional
        BigDecimal amountMonthly = sumAllServices.getTotalSumServices().add(valueMonthly);

        SumAllValues sumAllValues = new SumAllValues();

        sumAllValues.setTotalDepreciation(sumAllDepreciation().getPriceEquipment());
        sumAllValues.setTotalFixedCosts(sumAllFixedCosts().getValueFixedCosts());
        sumAllValues.setTotalVariableCosts(sumAllVariableCosts().getValueVariableCosts());
        sumAllValues.setTotalEmployeeCosts(sumAllEmployees().getSalary());
        sumAllValues.setTotalCommission(sumAllCommission().getValuePercentage());
        sumAllValues.setTotalProfit(sumAllProfit().getValuePercentage());
        sumAllValues.setTotalTaxes(sumAllTaxes().getValuePercentage());
        sumAllValues.setTotalSumServices(sumAllServices.getTotalSumServices());
        sumAllValues.setTotalSumTaxesCommissionProfit(sumFindAllTaxes.getTotalSumTaxesCommissionProfit());
        sumAllValues.setTotalMonthly(amountMonthly);

        return sumAllValues;
    }


}
