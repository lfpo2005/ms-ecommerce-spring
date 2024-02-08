package dev.ecommerce.services.imp;

import dev.ecommerce.models.SubCategoryModel;
import dev.ecommerce.repositories.SubCategoryRepository;
import dev.ecommerce.services.SubCategoryService;
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
