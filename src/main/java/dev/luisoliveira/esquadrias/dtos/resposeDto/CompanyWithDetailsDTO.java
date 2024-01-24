package dev.luisoliveira.esquadrias.dtos.resposeDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.luisoliveira.esquadrias.models.UserModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class CompanyWithDetailsDTO {

    private UUID companyId;
    private String cnpj;
    private String stateRegistration;
    private String municipalRegistration;
    private String fantasyName;
    private String companyName;
    private String category;
    private String email;
    private String nameContact;
    private String site;
    private String description;
    private Boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt ;
   // private FeedstockModel feedstock;
    private UserModel responsibleUser;
    private Set<AddressRespDTO> addresses;
    private Set<PhoneRespDTO> phones;


}
