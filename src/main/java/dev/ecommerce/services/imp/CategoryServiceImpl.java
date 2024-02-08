package dev.ecommerce.services.imp;

import dev.ecommerce.models.CategoryModel;
import dev.ecommerce.services.CategoryService;
import dev.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public boolean existsByCategoryName(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }

    @Override
    public CategoryModel save(CategoryModel categoryModel) {
        return categoryRepository.save(categoryModel);
    }

    @Override
    public Optional<CategoryModel> findById(UUID categoryId) {
        return categoryRepository.findById(categoryId);
    }


}
