package dev.luisoliveira.msshoppingcart.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.luisoliveira.msshoppingcart.enums.ActionType;
import dev.luisoliveira.msshoppingcart.model.ProductCart;
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
    private BigDecimal price;
    private BigDecimal promotionalPrice;
    private boolean promotional;
    private ActionType actionType;


    public ProductCart convertToProductCart(){
        var productCart = new ProductCart();
        BeanUtils.copyProperties(this, productCart);
        return productCart;
    }
}
