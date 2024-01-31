package dev.calculator.models;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionProductModel {

    public double estimatedTimeProductionInHours;
    public Integer quantityProduction;

}