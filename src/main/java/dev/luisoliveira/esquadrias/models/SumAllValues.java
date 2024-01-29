package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Setter
public class SumAllValues implements Serializable {
    private static final long serialVersionUID = 1L;


    @JsonIgnore
    private BigDecimal totalDepreciation;
    @JsonIgnore
    private BigDecimal totalFixedCosts;
    @JsonIgnore
    private BigDecimal totalVariableCosts;
    @JsonIgnore
    private BigDecimal totalEmployeeCosts;
    @JsonIgnore
    private Integer totalTaxes;
    @JsonIgnore
    private Integer totalProfit;
    @JsonIgnore
    private Integer totalCommission;

    private Integer totalSumTaxes;
    private BigDecimal totalSumServices;
    private BigDecimal totalMonthly;

}
