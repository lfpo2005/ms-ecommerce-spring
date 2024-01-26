package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.br.CNPJ;

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
@Table(name = "TB_COMPANIES")
public class CompanyModel implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID companyId;
        @CNPJ(message = "Cnpj is invalid")
        @Column(nullable = false, unique = true, length = 20)
        private String cnpj;
        @Column(unique = true, length = 20)
        private String stateRegistration;
        @Column(length = 20)
        private String municipalRegistration;
        @Column(nullable = false, length = 50)
        private String fantasyName;
        @Column(nullable = false, length = 50)
        private String CompanyName;
        @Column(nullable = false, length = 50)
        private String category;
        @Column(nullable = false, length = 60)
        private String email;
        @Column(nullable = false, length = 50)
        private String nameContact;
        @Column(length = 50)
        private String site;
        @Size(max = 500)
        private String description;
        @JsonIgnore
        @Column(nullable = false)
        private Boolean active = true;
        @JsonIgnore
        @Column(nullable = false)
        private Boolean deleted = false;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime updateAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime deleteAt;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        @Fetch(FetchMode.SUBSELECT)
        private Set<PhoneModel> phones = new HashSet<>();

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        @Fetch(FetchMode.SUBSELECT)
        private Set<AddressModel> address = new HashSet<>();

        @JsonIgnore
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "feedstock_id", referencedColumnName = "feedstockId")
        private FeedstockModel feedstock;

        @JsonBackReference
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private UserModel responsibleUser;

        @JsonIgnore
        @OneToMany(mappedBy = "company")
        private Set<EmployeeModel> employees = new HashSet<>();
}
