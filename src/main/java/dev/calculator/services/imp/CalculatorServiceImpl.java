package dev.calculator.services.imp;

import dev.calculator.exceptions.UserNotFoundException;
import dev.calculator.repositories.*;
import dev.calculator.models.CalculatorSumModel;
import dev.calculator.models.UserModel;
import dev.calculator.services.CalculatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


    @Override
    public CalculatorSumModel totalMonthly(UUID userId) {
        try {
            BigDecimal depreciation = depreciationRepository.priceEquipment(userId) != null ? depreciationRepository.priceEquipment(userId) : BigDecimal.ZERO;
            BigDecimal fixedCosts = fixedCostRepository.valueFixedCosts(userId) != null ? fixedCostRepository.valueFixedCosts(userId) : BigDecimal.ZERO;
            BigDecimal variableCosts = variableCostRepository.valueVariableCosts(userId) != null ? variableCostRepository.valueVariableCosts(userId) : BigDecimal.ZERO;
            BigDecimal employeeCosts = employeeRepository.valueEmployees(userId) != null ? employeeRepository.valueEmployees(userId) : BigDecimal.ZERO;

            BigDecimal totalSumServices = depreciation
                    .add(fixedCosts)
                    .add(variableCosts)
                    .add(employeeCosts);

            Integer taxes = taxesRepository.sumAllTaxesValues(userId) != null ? taxesRepository.sumAllTaxesValues(userId) : 0;
            Integer profit = profitRepository.sumAllProfitValues(userId) != null ? profitRepository.sumAllProfitValues(userId) : 0;
            Integer commission = commissionRepository.sumAllCommissionValues(userId) != null ? commissionRepository.sumAllCommissionValues(userId) : 0;

            BigDecimal taxesBigDecimal = new BigDecimal(taxes);
            BigDecimal profitBigDecimal = new BigDecimal(profit);
            BigDecimal commissionBigDecimal = new BigDecimal(commission);

            BigDecimal percentage = taxesBigDecimal
                    .add(profitBigDecimal)
                    .add(commissionBigDecimal)
                    .divide(new BigDecimal(100));

            BigDecimal amountMonthly = totalSumServices.multiply(percentage).add(totalSumServices);
            CalculatorSumModel calculatorSumModel = new CalculatorSumModel();
            calculatorSumModel.setTotalDepreciation(depreciation.setScale(2, RoundingMode.HALF_UP));
            calculatorSumModel.setTotalFixedCosts(fixedCosts.setScale(2, RoundingMode.HALF_UP));
            calculatorSumModel.setTotalVariableCosts(variableCosts.setScale(2, RoundingMode.HALF_UP));
            calculatorSumModel.setTotalEmployeeCosts(employeeCosts.setScale(2, RoundingMode.HALF_UP));
            calculatorSumModel.setTotalSumServices(totalSumServices.setScale(2, RoundingMode.HALF_UP));
            calculatorSumModel.setTotalTaxes(taxes);
            calculatorSumModel.setTotalProfit(profit);
            calculatorSumModel.setTotalCommission(commission);
            calculatorSumModel.setTotalSumTaxesCommissionProfit(taxes + profit + commission);
            calculatorSumModel.setTotalMonthly(amountMonthly.setScale(2, RoundingMode.HALF_UP));
            calculatorSumModel.setValueWorkDay(amountMonthly.divide(new BigDecimal(22), 2, BigDecimal.ROUND_HALF_UP));
            calculatorSumModel.setValueWorkHour(calculatorSumModel.getValueWorkDay().divide(new BigDecimal(8), 2, BigDecimal.ROUND_HALF_UP));
            return calculatorSumModel;
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

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
}

