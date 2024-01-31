package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COMMISSIONS")
public class CommissionModel  extends BaseFieldsCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID commissionId;

    private String sellerName;

}
