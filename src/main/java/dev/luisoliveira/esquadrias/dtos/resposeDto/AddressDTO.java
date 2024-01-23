package dev.luisoliveira.esquadrias.dtos.resposeDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.luisoliveira.esquadrias.enums.AddressType;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressDTO {

    private UUID addressId;
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    private String complement;
    private String neighborhood;
    private String description;
    private boolean active;
    private AddressType type;


    public void getType(String string) {
    }
}
