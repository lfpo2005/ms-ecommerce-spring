package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {


    public interface AddressView{
        public static interface RegistrationPost {}
        public static interface UserPut {}
    }

    private UUID addressId;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String street;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String number;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String city;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String state;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String zipCode;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String complement;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String neighborhood;

    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
    private String description;
}
