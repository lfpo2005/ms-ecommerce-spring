package dev.ecommerce.services;

import dev.ecommerce.models.CategoryModel;

import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    boolean existsByCategoryName(String categoryName);

    CategoryModel save(CategoryModel categoryModel);


    Optional<CategoryModel> findById(UUID categoryId);
}
