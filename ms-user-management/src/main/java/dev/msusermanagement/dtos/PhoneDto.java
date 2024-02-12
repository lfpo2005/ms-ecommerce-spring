package dev.msusermanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import dev.msusermanagement.enums.PhoneType;
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
    @NotBlank(groups =  {PhoneView.RegistrationPost.class, PhoneView.UserPut.class})
    @JsonView({PhoneView.RegistrationPost.class, PhoneView.UserPut.class})
    private String phoneNumber;
    @JsonView({PhoneView.RegistrationPost.class, PhoneView.UserPut.class})
    private PhoneType phoneType;
    @JsonView({PhoneView.RegistrationPost.class, PhoneView.UserPut.class})
    private String description;
    
    
}
