package dev.luisoliveira.msproductmanagement.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.msproductmanagement.configurations.security.UserDetailsImpl;
import dev.luisoliveira.msproductmanagement.dtos.SubCategoryDto;
import dev.luisoliveira.msproductmanagement.exceptions.NotFoundException;
import dev.luisoliveira.msproductmanagement.models.CategoryModel;
import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;
import dev.luisoliveira.msproductmanagement.models.UserModel;
import dev.luisoliveira.msproductmanagement.services.CategoryService;
import dev.luisoliveira.msproductmanagement.services.SubCategoryService;
import dev.luisoliveira.msproductmanagement.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/sub-categories")
public class SubCategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create-sub-category")
    public ResponseEntity<Object> registerSubCategory(@RequestBody
                                                      @Validated(SubCategoryDto.SubCategoryView.SubCategoryPost.class)
                                                      @JsonView(SubCategoryDto.SubCategoryView.SubCategoryPost.class)
                                                      SubCategoryDto subCategoryDto) {

        log.debug("POST registerSubCategory SubCategoryDto received: ------> {}", subCategoryDto.toString());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UserModel loggedInUser = userService.findById(userDetails.getUserId()).get();
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            UUID categoryId = subCategoryDto.getCategoryId() != null ? subCategoryDto.getCategoryId() : null;
            if (categoryId == null || !categoryService.findById(categoryId).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
            if (subCategoryService.existsBySubCategoryName(subCategoryDto.getSubCategoryName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("SubCategory already exists");
            } else {
                var subCategoryModel = new SubCategoryModel();
                BeanUtils.copyProperties(subCategoryDto, subCategoryModel);

                CategoryModel categoryModel = categoryService.findById(categoryId).orElse(null);
                subCategoryModel.setCategory(categoryModel);
                subCategoryModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
                subCategoryModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
                log.info("POST registerCategory CategoryModel to be saved: ------> {}", subCategoryModel.toString());
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
