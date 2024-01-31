package dev.calculator.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDto {

    public interface CompanyView{
        public static interface CompanyPost {}
        public static interface CompanyPut {}
    }

   
    private UUID companyId;
    
    @NotBlank(groups =  {CompanyView.CompanyPost.class})
    @JsonView({CompanyView.CompanyPost.class})
    private String cnpj;

    @JsonView({CompanyView.CompanyPost.class})
    private String stateRegistration;

    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String municipalRegistration;

    @NotBlank(groups =  {CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String fantasyName;

    @NotBlank(groups =  {CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String companyName;

    @NotBlank(groups =  {CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String category;

    @NotBlank(groups =  {CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String email;

    @NotBlank(groups =  {CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String nameContact;

    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String site;

    @JsonView({CompanyView.CompanyPost.class, CompanyView.CompanyPut.class})
    private String description;
    
    
}
