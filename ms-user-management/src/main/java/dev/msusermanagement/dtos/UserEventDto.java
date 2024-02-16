package dev.msusermanagement.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import dev.msusermanagement.enums.UserType;
import dev.msusermanagement.models.AddressModel;
import dev.msusermanagement.models.PhoneModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEventDto {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private Boolean active;
    private Boolean deleted;
    private String userType;
    private String cpf;
    private String updateAt;
    private String deleteAt;
    private String actionType;
}
