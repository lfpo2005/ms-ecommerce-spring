package dev.ecommerce.repositories;

import dev.ecommerce.models.SubCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategoryModel, UUID> {
    boolean existsBySubCategoryName(String subCategoryName);
}
