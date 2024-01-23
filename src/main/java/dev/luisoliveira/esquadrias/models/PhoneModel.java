package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.luisoliveira.esquadrias.dtos.resposeDto.PhoneRespDTO;
import dev.luisoliveira.esquadrias.enums.PhoneType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PHONES")
public class PhoneModel implements Serializable {
    private static final long serialVersionUID = 1L;


    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID phoneId;
    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @JsonIgnore
    @Column(nullable = false)
    private boolean active = true;
    @JsonIgnore
    @Column(nullable = false)
    private boolean deleted = false;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    @Size(max = 500)
    private String description;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private UserModel user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private CompanyModel company;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private EmployeeModel employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierId")
    private SupplierModel supplier;

    public PhoneRespDTO convertToPhoneDTO() {
        PhoneRespDTO phoneRespDTO = new PhoneRespDTO();
        BeanUtils.copyProperties(this, phoneRespDTO);
        if (this.getPhoneType() != null) {
            phoneRespDTO.setPhoneType(this.getPhoneType().toString());
        }
        return phoneRespDTO;
    }
}
