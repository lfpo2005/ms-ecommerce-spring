package dev.luisoliveira.msproductmanagement.services.impl;


import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;
import dev.luisoliveira.msproductmanagement.repositories.SubCategoryRepository;
import dev.luisoliveira.msproductmanagement.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Override
    public boolean existsBySubCategoryName(String subCategoryName) {
        return subCategoryRepository.existsBySubCategoryName(subCategoryName);
    }

    @Override
    public SubCategoryModel save(SubCategoryModel subCategoryModel) {
        return subCategoryRepository.save(subCategoryModel);
    }
}
