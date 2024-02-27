package dev.luisoliveira.msproductmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.luisoliveira.msproductmanagement.enums.ActionType;
import dev.luisoliveira.msproductmanagement.models.ProductModel;
import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductEventDto {

    private UUID productId;
    private String sku;
    private String name;
    private String price;
    private String promotionalPrice;
    private String promotional;
    private String actionType;
}
