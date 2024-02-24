package dev.msusermanagement.services;

import dev.msusermanagement.models.UserModel;
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
    UserModel save(UserModel userModel);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByFullName(String fullName);
    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
    UserModel updatePassword(UserModel userModel);

    boolean existsByCpf(String encryptedCpf);
    UserModel saveUser(UserModel userModel);
    UserModel deactivateUser(UserModel userModel);
    UserModel saveActiveUser(UserModel userModel);
    UserModel saveUpdateUser(UserModel userModel);
    UserModel deactivateAndDeleteUser(UserModel userModel);
}
