package dev.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxesDto {

    public interface TaxesView{
        public static interface TaxesPost {}
        public static interface TaxesPut {}
    }


    private UUID taxesId;

    @JsonView({TaxesDto.TaxesView.TaxesPost.class, TaxesDto.TaxesView.TaxesPut.class})
    private String name;
    @JsonView({TaxesDto.TaxesView.TaxesPost.class, TaxesDto.TaxesView.TaxesPut.class})
    private String description;
    @JsonView({TaxesDto.TaxesView.TaxesPost.class, TaxesDto.TaxesView.TaxesPut.class})
    private BigDecimal valueMoney;
    @JsonView({TaxesDto.TaxesView.TaxesPost.class, TaxesDto.TaxesView.TaxesPut.class})
    private Integer valuePercentage;
}


