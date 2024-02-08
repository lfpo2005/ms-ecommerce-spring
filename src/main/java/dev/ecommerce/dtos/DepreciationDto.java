package dev.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepreciationDto {

    public interface DepreciationView{
        public static interface DepreciationPost {}
        public static interface DepreciationPut {}
    }
    
    private UUID depreciationId;
    
    @NotBlank(groups =  {DepreciationView.DepreciationPost.class, DepreciationView.DepreciationPut.class})
    @JsonView({DepreciationView.DepreciationPost.class, DepreciationView.DepreciationPut.class})
    private String equipment;
    @JsonView({DepreciationView.DepreciationPost.class, DepreciationView.DepreciationPut.class})
    private Integer quantityEquipment;
    @JsonView({DepreciationView.DepreciationPost.class, DepreciationView.DepreciationPut.class})
    private BigDecimal priceEquipment;
}
