package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDto {

    public interface PhoneView{
        public static interface RegistrationPost {}
        public static interface UserPut {}
    }


    private UUID phoneId;
    @NotBlank(groups =  {PhoneDto.PhoneView.RegistrationPost.class, PhoneDto.PhoneView.UserPut.class})
    @JsonView({PhoneDto.PhoneView.RegistrationPost.class, PhoneDto.PhoneView.UserPut.class})
    private String phoneNumber;
    @JsonView({PhoneDto.PhoneView.RegistrationPost.class, PhoneDto.PhoneView.UserPut.class})
    private PhoneType phoneType;
    @JsonView({PhoneDto.PhoneView.RegistrationPost.class, PhoneDto.PhoneView.UserPut.class})
    private String description;
    
    
}
