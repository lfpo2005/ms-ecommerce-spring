package dev.luisoliveira.msproductmanagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PRODUCTS")
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productId;
    @Column(length = 20)
    private String sku;
    @Size(min = 5, message = "Nome do produto deve ter mais de 10 letras")
    @NotNull(message = "Nome do produto deve ser informado")
    @Column(nullable = false, unique = true, length = 60)
    private String name;
    @Column(length = 12)
    private String color;
    @Column(length = 20)
    private String model;
    @Column(unique = true, length = 50)
    private String codeBar;
    @Column(unique = true)
    private String qrCode;
    @NotNull(message = "Descrição do produto deve ser informada")
    @Column(columnDefinition = "text", length = 2000, nullable = false)
    @Description("Descrição do produto")
    private String description;
    @JsonIgnore
    @Column(nullable = false)
    private Boolean active = true;
    @NotNull(message = "O tipo da unidade deve ser informado")
    @Column(nullable = false)
    private String typeUnit; //add em um enum
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal promotionalPrice = BigDecimal.ZERO;
    private Boolean promotional = false;
    @Column(length = 5)
    private String weight;
    @Column(length = 5)
    private String height;
    @Column(length = 5)
    private String width;
    @Column(length = 5)
    private String depth;
    private Integer saleQuantity = 0;
    @Column(columnDefinition = "text", length = 2000)
    private String imageUrl;
    @Column(columnDefinition = "text", length = 2000)
    private String imageThumbnailUrl;
    private Integer stockQuantity = 0;
    private Integer stockMinimum = 0;
    private Boolean alertStockQuantity = false;
    private Integer clickQuantity = 0;
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SubCategoryModel subCategory;
}