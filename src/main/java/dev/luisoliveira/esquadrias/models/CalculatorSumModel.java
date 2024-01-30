package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Entity
//@Table(name = "TB_CALCULATOR_SUM")
public class CalculatorSumModel implements Serializable {
    private static final long serialVersionUID = 1L;

//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID calculatorSumId;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    protected UserModel user;

    public void setUser(UUID userId) {
        this.user = new UserModel();
        this.user.setUserId(userId);
    }

}
