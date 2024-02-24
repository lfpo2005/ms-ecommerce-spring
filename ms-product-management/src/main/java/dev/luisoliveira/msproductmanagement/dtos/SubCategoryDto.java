package dev.luisoliveira.msproductmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubCategoryDto {

    public interface SubCategoryView {
        public static interface SubCategoryPost {}

        public static interface SubCategoryPut {}
    }

    @NotBlank(groups = {SubCategoryView.SubCategoryPost.class, SubCategoryView.SubCategoryPut.class})
    @JsonView({SubCategoryView.SubCategoryPost.class, SubCategoryView.SubCategoryPut.class})
    private String subCategoryName;
    @JsonView({SubCategoryView.SubCategoryPost.class, SubCategoryView.SubCategoryPut.class})
    private String description;


}
