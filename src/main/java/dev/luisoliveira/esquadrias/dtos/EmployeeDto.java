package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

    public interface EmployeeView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
    }


    private UUID employeeId;

    @NotBlank(groups =  {EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String fullName;
    @NotBlank(groups =  {EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String email;
    @NotBlank(groups =  {EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String phoneNumber;
    @CPF(groups = {UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class})
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String cpf;

    @NotBlank(groups =  {EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String birthDate;
    @NotBlank(groups =  {EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String workCardNumber;
    @NotBlank(groups =  {EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String function;
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private BigDecimal salary;
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private BigDecimal socialCharges;
    @JsonView({EmployeeDto.EmployeeView.RegistrationPost.class, EmployeeDto.EmployeeView.UserPut.class})
    private String description;

}
