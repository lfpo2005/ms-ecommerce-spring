package dev.luisoliveira.msproductmanagement.controllers;

import dev.luisoliveira.msproductmanagement.dtos.ProductDto;
import dev.luisoliveira.msproductmanagement.models.ProductModel;
import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;
import dev.luisoliveira.msproductmanagement.services.ProductService;
import dev.luisoliveira.msproductmanagement.services.SubCategoryService;
import dev.luisoliveira.msproductmanagement.services.UserService;
import dev.luisoliveira.msproductmanagement.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class ProductController {

    @Autowired
    SubCategoryService subCategoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("sub-categories/{subCategoryId}/products")
    public ResponseEntity<Object> registerProduct(@PathVariable("subCategoryId") UUID subCategoryId,
                                                  @RequestBody @Valid ProductDto productDto) {

        log.debug("POST registerProduct ProductDto received: ------> {}", productDto.toString());
        try {
            Optional<SubCategoryModel> subCategoryModelOptional = subCategoryService.findById(subCategoryId);
            if (!subCategoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubCategory not found");
            }
            if (productService.existsByName(productDto.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists");
            } else {
                var productModel = new ProductModel();
                BeanUtils.copyProperties(productDto, productModel);
                productModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
                productModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
                productModel.setSubCategory(subCategoryModelOptional.get());
                log.debug("ProductModel created: {} ", productModel.getProductId());
                log.info("Saving new product, productId: {} to the database", productModel.getProductId());
                productService.save(productModel);
                return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
            }
        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage());
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("sub-categories/{subCategoryId}/products")
    public ResponseEntity<Object> getProductsBySubCategory(@PathVariable("subCategoryId") UUID subCategoryId,
                                                           SpecificationTemplate.ProductSpec spec,
                                                            @PageableDefault(page = 0, size = 10, sort = "productId",
                                                            direction = Sort.Direction.ASC) Pageable pageable) {
        log.debug("GET getProductsBySubCategory subCategoryId received: ------> {}", subCategoryId);

        return ResponseEntity.status(HttpStatus.OK).body(productService
                                                            .findAllBySubCategory(SpecificationTemplate
                                                            .productSubCategoryId(subCategoryId)
                                                            .and(spec), pageable));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("sub-categories/{subCategoryId}/products/{productId}")
    public ResponseEntity<Object> getOneProduct(@PathVariable("subCategoryId") UUID subCategoryId,
                                               @PathVariable("productId") UUID productId) {

        Optional<ProductModel> productModelOptional = productService.findProductIntoSubCategory(subCategoryId, productId);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for this sub-category.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }
}

