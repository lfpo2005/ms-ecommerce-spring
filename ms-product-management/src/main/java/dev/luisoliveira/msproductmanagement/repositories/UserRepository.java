package dev.luisoliveira.msproductmanagement.repositories;

import dev.luisoliveira.msproductmanagement.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {


}
