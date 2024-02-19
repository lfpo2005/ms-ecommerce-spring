package dev.luisoliveira.msproductmanagement.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS_PRODUCTS")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable=false)
    private UUID userId;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable=false)
    private String userType;
    @Column(nullable=false)
    private Boolean active;
    @Column(nullable=false)
    private String updateAt;
}
