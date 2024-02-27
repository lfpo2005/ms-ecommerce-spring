package dev.luisoliveira.msshoppingcart.repositories;

import dev.luisoliveira.msshoppingcart.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {


}
