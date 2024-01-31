package dev.calculator.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.calculator.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_ADDRESSES")
public class AddressModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID addressId;
    @Column(length = 60, nullable = false)
    private String street;
    @Column(length = 6, nullable = false)
    private String number;
    @Column(length = 50, nullable = false)
    private String city;
    @Column(length = 2, nullable = false)
    private String state;
    @Column(length = 10, nullable = false)
    private String zipCode;
    @Column(length = 50)
    private String complement;
    @Column(length = 20, nullable = false)
    private String neighborhood;
    @Size(max = 500)
    private String description;
    @JsonIgnore
    @Column(nullable = false)
    private Boolean active = true;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType type;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private UserModel user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private CompanyModel company;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private EmployeeModel employee;

}
