package dev.calculator.services;

import dev.calculator.models.CategoryModel;

import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    boolean existsByCategoryName(String categoryName);

    CategoryModel save(CategoryModel categoryModel);


    Optional<CategoryModel> findById(UUID categoryId);
}
