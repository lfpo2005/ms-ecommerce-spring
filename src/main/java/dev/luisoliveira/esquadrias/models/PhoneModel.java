package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.luisoliveira.esquadrias.enums.PhoneType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PHONES")
public class PhoneModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID phoneId;
    @Column(nullable = false, length = 15)
    private String phoneNumber;
    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = false)
    private boolean isDeleted = false;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    @Size(max = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyModel company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierId")
    private SupplierModel supplier;

}
