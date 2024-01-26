package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FixedCostDto {

    public interface FixedCostView{
        public static interface FixedCostPost {}
        public static interface FixedCostPut {}
    }

    private UUID fixedCostId;

    @JsonView({FixedCostView.FixedCostPost.class, FixedCostView.FixedCostPut.class})
    private BigDecimal valueFixedCosts;

    @NotBlank (groups = {FixedCostView.FixedCostPost.class, FixedCostView.FixedCostPut.class})
    @JsonView({FixedCostView.FixedCostPost.class, FixedCostView.FixedCostPut.class})
    private String nameCosts;

    @JsonView({FixedCostView.FixedCostPost.class, FixedCostView.FixedCostPut.class})
    private String description;
}
