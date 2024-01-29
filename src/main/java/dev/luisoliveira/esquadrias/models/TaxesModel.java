package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
//    private TaxesModel sumFindAllTaxes() {
//        TaxesModel taxesModel = new TaxesModel();
//        taxesModel.setValuePercentage(taxesRepository.sumAllTaxesValues());
//        return taxesModel;
//    }