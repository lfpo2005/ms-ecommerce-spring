package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.io.Serializable;
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
        @CNPJ(message = "Cnpj está inválido")
        @Column(nullable = false, unique = true, length = 14)
        private String cnpj;
        @Column(nullable = false, unique = true, length = 14)
        private String stateRegistration;
        @Column(length = 14)
        private String municipalRegistration;
        @Column(nullable = false, length = 50)
        private String fantasyName;
        @Column(nullable = false, length = 50)
        private String corporateName;
        @Column(nullable = false, length = 50)
        private String category;
        private String description;
        @Column(nullable = false, length = 60)
        private String email;
        @Column(nullable = false, length = 50)
        private String nameContact;
        @Column(length = 50)
        private String site;

        @ManyToOne
        @JoinColumn(name = "phone_id", nullable = false)
        private PhoneModel phone;

        @ManyToOne
        @JoinColumn(name = "address_id", nullable = false)
        private AddressModel address;
}
