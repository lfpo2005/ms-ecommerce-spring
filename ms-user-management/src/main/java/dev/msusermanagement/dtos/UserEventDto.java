package dev.msusermanagement.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEventDto {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private String active;
    private String userType;
    private String cpf;
    private String updateAt;
    private String deleteAt;
    private String actionType;

}
