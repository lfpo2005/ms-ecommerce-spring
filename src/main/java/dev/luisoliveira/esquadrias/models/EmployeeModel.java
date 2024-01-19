package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
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
    private LocalDateTime admissionDate;
    private LocalDateTime dismissalDate;
    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = false)
    private boolean isDeleted = false;
    private String workCardNumber;
    private String function;
    private BigDecimal salary;
    private BigDecimal socialCharges;
    @Size(max = 500)
    private String description;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private UserModel user;

}
