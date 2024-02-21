package dev.msusermanagement.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AdminDto {

    @NotNull
    private UUID userId;
}
