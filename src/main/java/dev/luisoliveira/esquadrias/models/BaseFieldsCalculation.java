package dev.luisoliveira.esquadrias.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseFieldsCalculation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    @NotNull(message = "value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "value must be greater than 0")
    private BigDecimal valueMoney;
    private Integer valuePercentage;
}
