package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.luisoliveira.esquadrias.enums.MeasurementUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_FEEDSTOCKS")
public class FeedstockModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID feedstockId;
    private Double quantity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurementUnit unitOfMeasure;
    private BigDecimal pricePerUnit;
    @Size(max = 500)
    private String classDescription;
    @Size(max = 500)
    private String description;
    @JsonIgnore
    private boolean active = true;
    @JsonIgnore
    @Column(nullable = false)
    private boolean deleted = false;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime expirationDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CategoryModel category;

}
