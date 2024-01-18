package dev.luisoliveira.esquadrias.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.luisoliveira.esquadrias.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Email(message = "Invalid email format. Please enter a valid email.")
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;
    @Column(nullable = false, unique = true, length = 150)
    private String fullName;
    @Column(nullable = false)
    private boolean isActive = true;
    @Column(nullable = false)
    private boolean isDeleted = false;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @JsonIgnore
   // @CPF(message = "cpf invalid, default is 000.000.000-00")
    @Column(nullable = false)
    private String cpf;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    private String birthDate;
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime deleteAt;

    @Size(max = 500)
    private String description;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER )
    @Fetch(FetchMode.SUBSELECT)
    private Set<AddressModel> address = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneModel> phones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "responsibleUser")
    private Set<CompanyModel> companies;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    private EmployeeModel employee;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_USERS_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles = new HashSet<>();
}
