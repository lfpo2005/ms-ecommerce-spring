package dev.luisoliveira.msshoppingcart.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import dev.luisoliveira.msshoppingcart.enums.ActionType;
import dev.luisoliveira.msshoppingcart.model.UserModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEventDto {

    private UUID userId;
    private String fullName;
    private String userType;
    private Boolean active;
    private String updateAt;
    private ActionType actionType;

    public UserModel convertToUserModel(){
        var userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        return userModel;
    }}