package dev.msusermanagement.dtos;


import dev.msusermanagement.enums.UserType;
import dev.msusermanagement.models.AddressModel;
import dev.msusermanagement.models.PhoneModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserEventDto {

    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private Boolean active;
    private Boolean deleted;
    private Boolean business;
    private String userType;
    private String cpf;
    private String imageUrl;
    private String birthDate;
    private String updateAt;
    private Set<AddressModel> address = new HashSet<>();
    private Set<PhoneModel> phones = new HashSet<>();
    private String actionType;
}
