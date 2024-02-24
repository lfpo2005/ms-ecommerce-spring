package dev.luisoliveira.msproductmanagement.services;


import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;

import java.util.Optional;
import java.util.UUID;

public interface SubCategoryService {
    boolean existsBySubCategoryName(String subCategoryName);

    SubCategoryModel save(SubCategoryModel subCategoryModel);

    Optional<SubCategoryModel> findById(UUID subCategoryId);
}
