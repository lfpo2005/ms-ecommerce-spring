package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDto {

    public interface CompanyView{
        public static interface RegistrationPost {}
        public static interface UserPut {}
    }

   
    private UUID companyId;
    
    @NotBlank(groups =  {CompanyDto.CompanyView.RegistrationPost.class})
    @JsonView({CompanyDto.CompanyView.RegistrationPost.class})
    private String cnpj;

    @JsonView({CompanyDto.CompanyView.RegistrationPost.class})
    private String stateRegistration;

    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String municipalRegistration;

    @NotBlank(groups =  {CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String fantasyName;

    @NotBlank(groups =  {CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String corporateName;

    @NotBlank(groups =  {CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String category;

    @NotBlank(groups =  {CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String email;

    @NotBlank(groups =  {CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String nameContact;

    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String site;

    @JsonView({CompanyDto.CompanyView.RegistrationPost.class, CompanyDto.CompanyView.UserPut.class})
    private String description;
    
    
}
