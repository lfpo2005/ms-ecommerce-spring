package dev.calculator.services;

import dev.calculator.models.SubCategoryModel;

public interface SubCategoryService {
    boolean existsBySubCategoryName(String subCategoryName);

    SubCategoryModel save(SubCategoryModel subCategoryModel);
}
