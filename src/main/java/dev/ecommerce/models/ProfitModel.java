package dev.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PROFITS")
public class ProfitModel extends BaseFieldsCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID profitId;

}
