package dev.luisoliveira.msproductmanagement.services;


import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;

public interface SubCategoryService {
    boolean existsBySubCategoryName(String subCategoryName);

    SubCategoryModel save(SubCategoryModel subCategoryModel);
}
