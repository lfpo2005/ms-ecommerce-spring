package dev.luisoliveira.storejava.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PRODUCTS")
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productId;
    @Column(nullable = false, unique = true, length = 20)
    private String sku;
    @Size(min = 10, message = "Nome do produto deve ter mais de 10 letras")
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
    private String description;
    @NotNull(message = "O tipo da unidade deve ser informado")
    @Column(nullable = false)
    private String typeUnit;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal promotionalPrice = BigDecimal.ZERO;
    private boolean promotional = false;
    private boolean active;
    @Column(nullable = false, unique = true, length = 5)
    private String weight;
    @Column(nullable = false, unique = true, length = 5)
    private String height;
    @Column(nullable = false, unique = true, length = 5)
    private String width;
    @Column(nullable = false, unique = true, length = 5)
    private String depth;
    private Integer saleQuantity = 0;
    @Column(columnDefinition = "text", length = 2000)
    private String imageUrl;
    @Column(columnDefinition = "text", length = 2000, nullable = false)
    private String imageThumbnailUrl;
    private Integer stockQuantity = 0;
    private Integer stockMinimum = 0;
    private boolean alertStockQuantity = false;
    private Integer clickQuantity = 0;
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant updateAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant deleteAt;

    @NotNull(message = "A Categoria do Produto deve ser informada")
    @ManyToOne(targetEntity = CategoryModel.class)
    @JoinColumn(name = "category_product_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "category_product_id_fk"))
    private CategoryModel category = new CategoryModel();

}