package dev.luisoliveira.esquadrias.dtos.resposeDto;

import dev.luisoliveira.esquadrias.enums.PhoneType;
import dev.luisoliveira.esquadrias.models.PhoneModel;
import lombok.Data;

import java.util.UUID;

@Data
public class PhoneDTO {

    private UUID phoneId;
    private String phoneNumber;
    private boolean active = true;
    private PhoneType phoneType;
    private String description;

    public void setPhoneType(String typeString) {
        this.phoneType = PhoneType.valueOf(typeString);
    }
}
