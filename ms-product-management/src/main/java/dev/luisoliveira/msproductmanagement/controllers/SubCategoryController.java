package dev.luisoliveira.msproductmanagement.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.msproductmanagement.dtos.SubCategoryDto;
import dev.luisoliveira.msproductmanagement.exceptions.NotFoundException;
import dev.luisoliveira.msproductmanagement.models.CategoryModel;
import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;
import dev.luisoliveira.msproductmanagement.services.CategoryService;
import dev.luisoliveira.msproductmanagement.services.SubCategoryService;
import dev.luisoliveira.msproductmanagement.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class SubCategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("categories/{categoryId}/sub-categories")
    public ResponseEntity<Object> registerSubCategory(@PathVariable("categoryId") UUID categoryId,
                                                      @RequestBody
                                                      @Validated(SubCategoryDto.SubCategoryView.SubCategoryPost.class)
                                                      @JsonView(SubCategoryDto.SubCategoryView.SubCategoryPost.class)
                                                      SubCategoryDto subCategoryDto) {

        log.debug("POST registerSubCategory SubCategoryDto received: ------> {}", subCategoryDto.toString());
        try {
            Optional<CategoryModel> categoryModelOptional = categoryService.findById(categoryId);
            if (!categoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
            if (subCategoryService.existsBySubCategoryName(subCategoryDto.getSubCategoryName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("SubCategory already exists");
            } else {
                var subCategoryModel = new SubCategoryModel();
                BeanUtils.copyProperties(subCategoryDto, subCategoryModel);
                subCategoryModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
                subCategoryModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
                subCategoryModel.setCategory(categoryModelOptional.get());
                log.debug("SubCategoryModel created: {} ", subCategoryModel.getSubCategoryId());
                log.info("POST registerCategory CategoryModel to be saved: ------> {}", subCategoryModel.getSubCategoryId());
                subCategoryService.save(subCategoryModel);
                return ResponseEntity.status(HttpStatus.CREATED).body(subCategoryModel);
            }
        } catch (NotFoundException t) {
            log.error("POST registerSubCategory error: ------> {}", t.getMessage());
            throw t;
        } catch (Exception e) {
            log.error("POST registerSubCategory error: ------> {}", e.getMessage());
            throw e;
        }
    }
}
