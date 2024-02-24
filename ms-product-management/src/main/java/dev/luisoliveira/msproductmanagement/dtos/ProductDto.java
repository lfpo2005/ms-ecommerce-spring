package dev.luisoliveira.msproductmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private String sku;
    private String name;
    private String color;
    private String model;
    private String codeBar;
    private String qrCode;
    private String description;
    private String classDescription;
    private Boolean active = true;
    private String typeUnit;
    private BigDecimal price;
    private BigDecimal promotionalPrice;
    private Boolean promotional = false;
    private String weight;
    private String height;
    private String width;
    private String depth;
    private Integer saleQuantity;
    private String imageUrl;
    private String imageThumbnailUrl;
    private Integer stockQuantity;
    private Integer stockMinimum;
    private Boolean alertStockQuantity;
    private Integer clickQuantity;

    private SubCategoryModel subCategory;


}
