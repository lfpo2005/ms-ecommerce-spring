package dev.luisoliveira.msproductmanagement.repositories;

import dev.luisoliveira.msproductmanagement.models.CategoryModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID>{
    boolean existsByCategoryName(String categoryName);

    @EntityGraph(attributePaths = "subCategories", type = EntityGraph.EntityGraphType.FETCH)
    Optional<CategoryModel> findById(UUID categoryId);

}
