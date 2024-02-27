package dev.luisoliveira.msshoppingcart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PRODUCT_CART")
public class ProductCart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID productId;
    private String name;
    private String sku;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal promotionalPrice = BigDecimal.ZERO;
    private boolean promotional;
}
