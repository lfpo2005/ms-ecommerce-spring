package dev.luisoliveira.storejava.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.luisoliveira.storejava.enums.AddressType;
import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_ADDRESS")
public class AddressModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID addressId;
    @Column(length = 60)
    private String address;
    @Column(length = 6)
    private String number;
    @Column(length = 50)
    private String city;
    @Column(length = 2)
    private String state;
    @Column(length = 10)
    private String zipCode;
    @Column(length = 50)
    private String complement;
    @Column(length = 20)
    private String neighborhood;

    @Column(nullable = false)
    private AddressType type;
}
