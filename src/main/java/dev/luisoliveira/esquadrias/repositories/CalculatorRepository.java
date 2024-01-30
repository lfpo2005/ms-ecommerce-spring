package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.CalculatorSumModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CalculatorRepository /*extends JpaRepository<CalculatorSumModel, UUID>*/ {

    CalculatorSumModel findByUser_UserId(UUID userId);

    boolean existsByUser_UserId(UUID userId);
}
