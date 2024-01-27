package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
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
