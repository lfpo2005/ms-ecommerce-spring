package dev.luisoliveira.esquadrias.dtos;

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
        public static interface RegistrationPost {}
        public static interface UserPut {}
    }
    
    private UUID depreciationId;
    
    @NotBlank(groups =  {DepreciationDto.DepreciationView.RegistrationPost.class, DepreciationDto.DepreciationView.UserPut.class})
    @JsonView({DepreciationDto.DepreciationView.RegistrationPost.class, DepreciationDto.DepreciationView.UserPut.class})
    private String equipment;
    @NotBlank(groups =  {DepreciationDto.DepreciationView.RegistrationPost.class, DepreciationDto.DepreciationView.UserPut.class})
    @JsonView({DepreciationDto.DepreciationView.RegistrationPost.class, DepreciationDto.DepreciationView.UserPut.class})
    private String quantityEquipment;
    @JsonView({DepreciationDto.DepreciationView.RegistrationPost.class, DepreciationDto.DepreciationView.UserPut.class})
    private BigDecimal value;
}
