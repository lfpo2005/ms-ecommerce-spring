package dev.calculator.services;

import dev.calculator.exceptions.CalculationException;
import dev.calculator.models.CalculatorSumModel;

import java.util.Optional;
import java.util.UUID;

public interface CalculatorService {

    CalculatorSumModel totalMonthly(UUID userId) throws CalculationException;
    Optional<CalculatorSumModel> findById(UUID userId);

}
