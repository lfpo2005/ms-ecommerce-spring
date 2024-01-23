package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_EMPLOYEES")
public class EmployeeModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID employeeId;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 20)
    private String phoneNumber;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false, updatable = false)
    private LocalDateTime admissionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime dismissalDate;
    @JsonIgnore
    @Column(nullable = false)
    private boolean active = true;
    @JsonIgnore
    @Column(nullable = false)
    private boolean deleted = false;
    private String workCardNumber;
    private String function;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    private BigDecimal salary;

    @NotNull(message = "socialCharges is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "socialCharges must be greater than 0")
    private BigDecimal socialCharges;
    @Size(max = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyModel company;

}
