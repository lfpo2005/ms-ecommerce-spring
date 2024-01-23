package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_SUPPLIERS")
public class SupplierModel implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID supplierId;
        @Column(nullable = false, length = 50)
        private String nameContact;
        @JsonIgnore
        @Column(nullable = false)
        private boolean active = true;
        @JsonIgnore
        @Column(nullable = false)
        private boolean deleted = false;
        @Size(max = 500)
        private String description;
        @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<PhoneModel> phones = new HashSet<>();

        @ManyToOne
        @JoinColumn(name = "address_id", nullable = false)
        private AddressModel address;
}
