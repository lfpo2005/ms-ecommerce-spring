package dev.luisoliveira.esquadrias.services;

import dev.luisoliveira.esquadrias.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    Optional<UserModel> findByIdWithAddressesAndPhones(UUID userId);

    void delete(UserModel userModel);

    UserModel save(UserModel userModel);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByFullName(String fullName);

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

   // UserModel saveUser(UserModel userModel);

  //  void deleteUser(UserModel userModel);

    UserModel updatePassword(UserModel userModel);

    UserModel updateUser(UserModel userModel);
}
