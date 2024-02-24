package dev.luisoliveira.msproductmanagement.services;

import dev.luisoliveira.msproductmanagement.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    ProductModel save(ProductModel productModel);

    boolean existsByName(String name);

    Page<ProductModel> findAllBySubCategory(Specification<ProductModel> spec, Pageable pageable);

    Optional<ProductModel> findProductIntoSubCategory(UUID subCategoryId, UUID productId);
}
