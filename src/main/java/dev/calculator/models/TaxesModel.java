package dev.calculator.models;

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
@Table(name = "TB_TAXES")
public class TaxesModel extends BaseFieldsCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID taxesId;

}
