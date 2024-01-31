package dev.calculator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseFieldsCalculation implements Serializable {
    private static final long serialVersionUID = 1L;

    protected  String name;
    protected  String description;
    protected  Integer valuePercentage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    protected UserModel user;

    public void setUser(UUID userId) {
        this.user = new UserModel();
        this.user.setUserId(userId);
    }
}
