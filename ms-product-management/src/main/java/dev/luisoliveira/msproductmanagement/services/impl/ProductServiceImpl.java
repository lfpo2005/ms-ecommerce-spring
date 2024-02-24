package dev.luisoliveira.msproductmanagement.services.impl;

import dev.luisoliveira.msproductmanagement.models.ProductModel;
import dev.luisoliveira.msproductmanagement.repositories.ProductRepository;
import dev.luisoliveira.msproductmanagement.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    @Override
    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public Page<ProductModel> findAllBySubCategory(Specification<ProductModel> spec, Pageable pageable) {
        return productRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<ProductModel> findProductIntoSubCategory(UUID subCategoryId, UUID productId) {
        return productRepository.findProductIntoSubCategory(subCategoryId, productId);
    }


}
