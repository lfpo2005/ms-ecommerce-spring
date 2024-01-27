package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariableCostDto {

    public interface VariableCostView {
        public static interface VariableCostPost {}
        public static interface VariableCostPut {}
    }

    private UUID variableCostId;

    @JsonView({VariableCostView.VariableCostPost.class, VariableCostView.VariableCostPut.class})
    private BigDecimal valueVariableCosts;

    @NotBlank (groups = {VariableCostView.VariableCostPost.class, VariableCostView.VariableCostPut.class})
    @JsonView({VariableCostView.VariableCostPost.class, VariableCostView.VariableCostPut.class})
    private String nameCosts;

    @JsonView({VariableCostView.VariableCostPost.class, VariableCostView.VariableCostPut.class})
    private String description;
}
