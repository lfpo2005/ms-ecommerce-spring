package dev.ecommerce.models;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionProductModel {

    public BigDecimal estimatedTimeProductionInMinutes;
    public BigDecimal estimatedTimeProductionInHours;
    public BigDecimal estimatedTimeProductionInDays;
    public Integer quantityProduction;

}