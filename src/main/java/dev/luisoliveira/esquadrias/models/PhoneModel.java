package dev.luisoliveira.storejava.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.luisoliveira.storejava.enums.PhoneType;
import javax.persistence.*;
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

    private PhoneType phoneType;

}
