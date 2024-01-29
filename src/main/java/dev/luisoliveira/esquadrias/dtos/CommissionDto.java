package dev.luisoliveira.esquadrias.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommissionDto {

    public interface CommissionView{
        public static interface CommissionPost {}
        public static interface CommissionPut {}
    }


    private UUID commissionId;

    @JsonView({CommissionView.CommissionPost.class, CommissionView.CommissionPut.class})
    private String sellerName;
    @JsonView({CommissionView.CommissionPost.class, CommissionView.CommissionPut.class})
    private String name;
    @JsonView({CommissionView.CommissionPost.class, CommissionView.CommissionPut.class})
    private String description;
    @JsonView({CommissionView.CommissionPost.class, CommissionView.CommissionPut.class})
    private BigDecimal valueMoney;
    @JsonView({CommissionView.CommissionPost.class, CommissionView.CommissionPut.class})
    private Integer valuePercentage;
}
