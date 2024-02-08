package dev.ecommerce.services;

import dev.ecommerce.exceptions.CalculationException;
import dev.ecommerce.models.CalculatorSumModel;

import java.util.Optional;
import java.util.UUID;

public interface CalculatorService {

    CalculatorSumModel totalMonthly(UUID userId) throws CalculationException;
    Optional<CalculatorSumModel> findById(UUID userId);

}
