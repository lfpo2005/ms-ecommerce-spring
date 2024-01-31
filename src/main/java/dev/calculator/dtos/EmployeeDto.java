package dev.calculator.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.calculator.models.AddressModel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

    public interface EmployeeView {
        public static interface EmployeePost {}
        public static interface EmployeePut {}
        public static interface ReasonDismissalPut {}
    }

    @JsonView({EmployeeView.EmployeePut.class})
    private UUID employeeId;

    @NotBlank(groups = {EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private String fullName;
    @NotBlank(groups = {EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private String email;
    @NotBlank(groups = {EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private String phoneNumber;

    @CPF(groups = {UserDto.UserView.RegistrationPost.class})
    @JsonView({EmployeeView.EmployeePost.class})
    private String cpf;

    @NotBlank(groups =  {EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private String birthDate;
    @NotBlank(groups =  {EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private String workCardNumber;
    @NotBlank(groups =  {EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private String function;
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private BigDecimal salary;
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private BigDecimal socialCharges;
    @JsonView({EmployeeView.EmployeePost.class, EmployeeView.EmployeePut.class})
    private String description;
    @JsonView({EmployeeView.EmployeePut.class})
    private Set<AddressModel> address = new HashSet<>();

    @JsonView({EmployeeView.ReasonDismissalPut.class})
    private String reasonDismissal;

}
