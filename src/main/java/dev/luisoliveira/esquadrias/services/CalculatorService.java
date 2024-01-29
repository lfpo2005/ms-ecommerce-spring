package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.SumAllValues;

import java.math.BigDecimal;

public interface CalculatorService {
/*    BigDecimal sumAllDepreciationValues();

    BigDecimal sumAllFixedCostsValues();

    BigDecimal sumAllVariableCostsValues();

    BigDecimal sumAllEmployeesValues();

    Integer sumAllTaxesValues();

    Integer sumAllProfitValues();

    Integer sumAllCommissionValues();*/

    SumAllValues sumAllServices();

    SumAllValues sumFindAllTaxes();

    SumAllValues totalMonthly();
}
