package dev.calculator.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_ESQUADRIAS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EsquadriaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID esquadriaId;
    private String titulo;
    private List<String> perfil;
    private List<String> descricao;
    private List<Integer> quantidade;
    private Integer quantidadeProducao;
    private Integer tamanho;
    private List<String> referencia;
    private List<String> grau;
    private Integer largura;
    private Integer altura;
    private Integer area;

    @OneToMany(mappedBy = "esquadria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcessorioModel> acessorios;
}

