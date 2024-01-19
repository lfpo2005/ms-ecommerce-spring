package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyModel, UUID> {
}
