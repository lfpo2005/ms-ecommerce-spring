package dev.luisoliveira.msshoppingcart.service;

import dev.luisoliveira.msshoppingcart.model.UserModel;

import java.util.Optional;
import java.util.UUID;


public interface UserService {
    UserModel save(UserModel userModel);

    void delete(UUID userId);

    Optional<UserModel> findById(UUID userId);
}
