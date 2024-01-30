package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.exceptions.UserNotFoundException;
import dev.luisoliveira.esquadrias.models.CalculatorSumModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.repositories.*;
import dev.luisoliveira.esquadrias.services.CalculatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    CalculatorRepository calculatorRepository;

    @Override
    public CalculatorSumModel totalMonthly(UUID userId) {
        try {
            BigDecimal depreciation = depreciationRepository.priceEquipment(userId) != null ? depreciationRepository.priceEquipment(userId) : BigDecimal.ZERO;
            BigDecimal fixedCosts = fixedCostRepository.valueFixedCosts(userId) != null ? fixedCostRepository.valueFixedCosts(userId) : BigDecimal.ZERO;
            BigDecimal variableCosts = variableCostRepository.valueVariableCosts(userId) != null ? variableCostRepository.valueVariableCosts(userId) : BigDecimal.ZERO;
            BigDecimal employeeCosts = employeeRepository.valueEmployees(userId) != null ? employeeRepository.valueEmployees(userId) : BigDecimal.ZERO;

            BigDecimal totalSumServices = depreciation.add(fixedCosts).add(variableCosts).add(employeeCosts);

            Integer taxes = taxesRepository.sumAllTaxesValues(userId) != null ? taxesRepository.sumAllTaxesValues(userId) : 0;
            Integer profit = profitRepository.sumAllProfitValues(userId) != null ? profitRepository.sumAllProfitValues(userId) : 0;
            Integer commission = commissionRepository.sumAllCommissionValues(userId) != null ? commissionRepository.sumAllCommissionValues(userId) : 0;

            BigDecimal taxesBigDecimal = new BigDecimal(taxes);
            BigDecimal profitBigDecimal = new BigDecimal(profit);
            BigDecimal commissionBigDecimal = new BigDecimal(commission);

            BigDecimal porcentagem = taxesBigDecimal.add(profitBigDecimal).add(commissionBigDecimal).divide(new BigDecimal(100));

            BigDecimal amountMonthly = totalSumServices.multiply(porcentagem).add(totalSumServices);

            CalculatorSumModel calculatorSumModel = new CalculatorSumModel();
            calculatorSumModel.setTotalDepreciation(depreciation);
            calculatorSumModel.setTotalFixedCosts(fixedCosts);
            calculatorSumModel.setTotalVariableCosts(variableCosts);
            calculatorSumModel.setTotalEmployeeCosts(employeeCosts);
            calculatorSumModel.setTotalSumServices(totalSumServices);
            calculatorSumModel.setTotalTaxes(taxes);
            calculatorSumModel.setTotalProfit(profit);
            calculatorSumModel.setTotalCommission(commission);
            calculatorSumModel.setTotalMonthly(amountMonthly);

            return calculatorSumModel;
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    /*@Override
    public CalculatorSumModel totalMonthly(UUID userId) {
        try {
            // Use métodos existentes para calcular partes do CalculatorSumModel
            BigDecimal depreciation = depreciationRepository.priceEquipment(userId);
            BigDecimal fixedCosts = fixedCostRepository.valueFixedCosts(userId);
            BigDecimal variableCosts = variableCostRepository.valueVariableCosts(userId);
            BigDecimal employeeCosts = employeeRepository.valueEmployees(userId);

            BigDecimal totalSumServices = depreciation.add(fixedCosts).add(variableCosts).add(employeeCosts);

            Integer taxes = taxesRepository.sumAllTaxesValues(userId);
            Integer profit = profitRepository.sumAllProfitValues(userId);
            Integer commission = commissionRepository.sumAllCommissionValues(userId);

            BigDecimal taxesBigDecimal = new BigDecimal(taxes);
            BigDecimal profitBigDecimal = new BigDecimal(profit);
            BigDecimal commissionBigDecimal = new BigDecimal(commission);

            BigDecimal porcentagem = taxesBigDecimal.add(profitBigDecimal).add(commissionBigDecimal).divide(new BigDecimal(100));

            BigDecimal amountMonthly = totalSumServices.multiply(porcentagem).add(totalSumServices);

            CalculatorSumModel calculatorSumModel = new CalculatorSumModel();
            calculatorSumModel.setTotalDepreciation(depreciation);
            calculatorSumModel.setTotalFixedCosts(fixedCosts);
            calculatorSumModel.setTotalVariableCosts(variableCosts);
            calculatorSumModel.setTotalEmployeeCosts(employeeCosts);
            calculatorSumModel.setTotalSumServices(totalSumServices);
            calculatorSumModel.setTotalTaxes(taxes);
            calculatorSumModel.setTotalProfit(profit);
            calculatorSumModel.setTotalCommission(commission);
            calculatorSumModel.setTotalMonthly(amountMonthly);

            return calculatorSumModel;
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }*/

    @Override
    public Optional<CalculatorSumModel> findById(UUID userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        CalculatorSumModel financialData = null;

        financialData = totalMonthly(userId);

        return Optional.ofNullable(financialData);
    }

//    @Override
//    public CalculatorSumModel save(CalculatorSumModel calculatorSumModel) {
//        return calculatorRepository.save(calculatorSumModel);
//    }

//    @Override
//    public CalculatorSumModel findByUser_UserId(UUID userId) {
//        return calculatorRepository.findByUser_UserId(userId);
//    }
//
//    @Override
//    public boolean existsByUser_UserId(UUID userId) {
//        return calculatorRepository.existsByUser_UserId(userId);
    }

