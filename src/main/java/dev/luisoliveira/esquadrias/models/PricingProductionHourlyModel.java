package dev.luisoliveira.esquadrias.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PricingProductionHourlyModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pricingProductionHourlyId;

    private FixedCostModel fixedCost;

    private List<EmployeeModel> employees;

    @Size(max = 500)
    private String description;
}
