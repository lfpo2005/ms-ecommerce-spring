package dev.luisoliveira.msproductmanagement.repositories;

import dev.luisoliveira.msproductmanagement.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID>, JpaSpecificationExecutor<ProductModel>{
    boolean existsByName(String name);

    @Query(value = "select * from tb_products where sub_category_sub_category_id = :subCategoryId and product_id = :productId", nativeQuery = true)
    Optional<ProductModel> findProductIntoSubCategory(@Param("subCategoryId") UUID subCategoryId,@Param("productId") UUID productId);
}
