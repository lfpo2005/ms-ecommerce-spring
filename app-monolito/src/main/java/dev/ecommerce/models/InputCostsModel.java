package dev.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.ecommerce.enums.MeasurementUnit;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_INPUT_COSTS")
public class InputCostsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID inputCostsId;
    private BigDecimal costPerUnit;
    private String quantityUnit;
    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;
    private BigDecimal producedQuantity;
    private BigDecimal yieldPerUnit;
    private String description;

    //TODO: Create the metods to calculate the cost per unit

}
