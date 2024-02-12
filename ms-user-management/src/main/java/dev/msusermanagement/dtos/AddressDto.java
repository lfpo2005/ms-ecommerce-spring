package dev.msusermanagement.dtos;

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

    @NotBlank(groups =  {AddressView.RegistrationPost.class, AddressView.UserPut.class})
    @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String street;

    @NotBlank(groups =  {AddressView.RegistrationPost.class, AddressView.UserPut.class})
    @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String number;

    @NotBlank(groups =  {AddressView.RegistrationPost.class, AddressView.UserPut.class})
    @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String city;

    @NotBlank(groups =  {AddressView.RegistrationPost.class, AddressView.UserPut.class})
    @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String state;

    @NotBlank(groups =  {AddressView.RegistrationPost.class, AddressView.UserPut.class})
    @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String zipCode;

   @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String complement;

    @NotBlank(groups =  {AddressView.RegistrationPost.class, AddressView.UserPut.class})
    @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String neighborhood;

    @JsonView({AddressView.RegistrationPost.class, AddressView.UserPut.class})
    private String description;

//    @NotBlank(groups =  {AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
//    @JsonView({AddressDto.AddressView.RegistrationPost.class, AddressDto.AddressView.UserPut.class})
//    private AddressType type;
}
