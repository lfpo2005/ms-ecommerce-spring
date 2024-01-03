package dev.luisoliveira.esquadrias.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_CATEGORIES")
public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID categoryId;
    @Column(nullable = false, unique = true, length = 60)
    private String name;
    @Size(max = 500)
    private String classDescription;
    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = false)
    private boolean isDeleted = false;
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant updateAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant deleteAt;


    @ManyToOne
    @JoinColumn(name = "category_parent_id")
    private CategoryModel parent;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY )
    @Fetch(FetchMode.SUBSELECT)
    private Set<ProductModel> products;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY )
    @Fetch(FetchMode.SUBSELECT)
    private Set<FeedstockModel> feedstocks;

}
