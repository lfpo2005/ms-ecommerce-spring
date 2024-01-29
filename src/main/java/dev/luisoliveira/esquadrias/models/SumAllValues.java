package dev.luisoliveira.esquadrias.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Setter
@Getter
public class SumAllValues implements Serializable {
    private static final long serialVersionUID = 1L;


    private BigDecimal totalDepreciation;
    private BigDecimal totalFixedCosts;
    private BigDecimal totalVariableCosts;
    private BigDecimal totalEmployeeCosts;
    private Integer totalTaxes;
    private Integer totalProfit;
    private Integer totalCommission;

    private Integer totalSumTaxesCommissionProfit;
    private BigDecimal totalSumServices;
    private BigDecimal totalMonthly;


}
