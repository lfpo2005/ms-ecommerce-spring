package dev.ecommerce.services;

import dev.ecommerce.models.SubCategoryModel;

public interface SubCategoryService {
    boolean existsBySubCategoryName(String subCategoryName);

    SubCategoryModel save(SubCategoryModel subCategoryModel);
}
