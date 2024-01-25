package dev.luisoliveira.esquadrias.dtos.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.luisoliveira.esquadrias.models.AddressModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class CompanyRespDTO {

    private UUID companyId;

    private String cnpj;
    private String stateRegistration;
    private String municipalRegistration;
    private String fantasyName;
    private String CompanyName;
    private String category;
    private String email;
    private String nameContact;
    private String site;
    private String description;
    private Boolean active = true;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateAt;
    private Set<PhoneModel> phones = new HashSet<>();
    private Set<AddressModel> address = new HashSet<>();
    private userRespDTO responsibleUser;

}
