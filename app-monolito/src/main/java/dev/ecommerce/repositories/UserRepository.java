package dev.ecommerce.repositories;

import dev.ecommerce.models.UserModel;
import org.springframework.data.jpa.repository.EntityGraph;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByFullName(String fullName);
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> findByUsername(String username);

    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> findById(UUID userId);

    @Query("SELECT u FROM UserModel u JOIN FETCH u.phones JOIN FETCH u.address WHERE u.userId = :userId")
    Optional<UserModel> findByIdWithAddressesAndPhones(@Param("userId") UUID userId);

}



