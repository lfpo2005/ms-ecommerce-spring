package dev.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubCategoryDto {

    public interface SubCategoryView {
        public static interface SubCategoryPost {}

        public static interface SubCategoryPut {}
    }

    //private UUID categoryId;
    @NotBlank(groups = {SubCategoryDto.SubCategoryView.SubCategoryPost.class, SubCategoryDto.SubCategoryView.SubCategoryPut.class})
    @JsonView({SubCategoryDto.SubCategoryView.SubCategoryPost.class, SubCategoryDto.SubCategoryView.SubCategoryPut.class})
    private String subCategoryName;
    @JsonView({SubCategoryDto.SubCategoryView.SubCategoryPost.class, SubCategoryDto.SubCategoryView.SubCategoryPut.class})
    private String description;

    @JsonView({SubCategoryView.SubCategoryPost.class, SubCategoryView.SubCategoryPut.class})
    private UUID categoryId;

}
