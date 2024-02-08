package dev.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ecommerce.models.SubCategoryModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {


    public interface CategoryView {
        public static interface CategoryPost {}

        public static interface CategoryPut {}
    }

    private UUID categoryId;
    @NotBlank(groups = {CategoryView.CategoryPost.class, CategoryView.CategoryPut.class})
    @JsonView({CategoryView.CategoryPost.class, CategoryView.CategoryPut.class})
    private String categoryName;
    @JsonView({CategoryView.CategoryPost.class, CategoryView.CategoryPut.class})
    private String description;
    @JsonView({CategoryView.CategoryPost.class, CategoryView.CategoryPut.class})
    private Set<SubCategoryModel> subCategories;

}
