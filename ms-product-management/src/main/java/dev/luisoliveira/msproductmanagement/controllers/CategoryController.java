package dev.luisoliveira.msproductmanagement.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.msproductmanagement.configurations.security.UserDetailsImpl;
import dev.luisoliveira.msproductmanagement.dtos.CategoryDto;
import dev.luisoliveira.msproductmanagement.models.CategoryModel;
import dev.luisoliveira.msproductmanagement.models.UserModel;
import dev.luisoliveira.msproductmanagement.services.CategoryService;
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

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create-category")
    public ResponseEntity<Object> registerCategory(@RequestBody
                                                   @Validated(CategoryDto.CategoryView.CategoryPost.class)
                                                   @JsonView(CategoryDto.CategoryView.CategoryPost.class)
                                                   CategoryDto categoryDto) {

            log.debug("POST registerCategory CategoryDto received: ------> {}", categoryDto.toString());
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                UserModel loggedInUser = userService.findById(userDetails.getUserId()).get();
                if (loggedInUser == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
                if (categoryService.existsByCategoryName(categoryDto.getCategoryName())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
                } else {
                    var categoryModel = new CategoryModel();
                    BeanUtils.copyProperties(categoryDto, categoryModel);
                    categoryModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
                    categoryModel.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));
                    log.info("POST registerCategory CategoryModel to be saved: ------> {}", categoryModel.toString());
                    categoryService.save(categoryModel);

                    return ResponseEntity.status(HttpStatus.CREATED).body(categoryModel);
                }

            } catch (Exception e) {
                log.error("POST registerCategory error: ------> {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    }
}
