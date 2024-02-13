package dev.msusermanagement.repositories;


import dev.msusermanagement.models.UserModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByFullName(String fullName);
    boolean existsByCpf(String encryptedCpf);
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> findByUsername(String username);

    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> findById(UUID userId);

    @Query(value = "SELECT u FROM UserModel u JOIN FETCH u.phones JOIN FETCH u.address WHERE u.userId = :userId")
//    @Query(value = "SELECT u.user_id AS u_user_id, u.username AS u_username, u.email AS u_email, \n" +
//            "               a.address_id AS a_address_id, a.city AS a_city, a.zip_code AS a_zip_code, \n" +
//            "               p.phone_id AS p_phone_id, p.phone_number AS p_number \n" +
//            "               FROM tb_users u \n" +
//            "               LEFT JOIN tb_addresses a ON u.user_id = a.user_user_id\n" +
//            "               LEFT JOIN tb_phones p ON u.user_id = p.user_user_id \n" +
//            "               WHERE u.user_id = :userId", nativeQuery = true)
    Optional<UserModel> findByIdWithAddressesAndPhones(@Param("userId") UUID userId);


}



