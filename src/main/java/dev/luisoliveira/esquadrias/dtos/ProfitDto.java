package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfitDto {


    public interface ProfitView{
        public static interface ProfitPost {}
        public static interface ProfitPut {}
    }


    private UUID profitId;

    @JsonView({ProfitDto.ProfitView.ProfitPost.class, ProfitDto.ProfitView.ProfitPut.class})
    private String name;
    @JsonView({ProfitDto.ProfitView.ProfitPost.class, ProfitDto.ProfitView.ProfitPut.class})
    private String description;
    @JsonView({ProfitDto.ProfitView.ProfitPost.class, ProfitDto.ProfitView.ProfitPut.class})
    private BigDecimal valueMoney;
    @JsonView({ProfitDto.ProfitView.ProfitPost.class, ProfitDto.ProfitView.ProfitPut.class})
    private Integer valuePercentage;
}
