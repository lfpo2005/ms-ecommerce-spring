package dev.luisoliveira.esquadrias.dtos.resposeDto;

import dev.luisoliveira.esquadrias.enums.AddressType;
import dev.luisoliveira.esquadrias.models.AddressModel;
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

    public void setType(String typeString) {
        this.type = AddressType.valueOf(typeString);
    }

}
