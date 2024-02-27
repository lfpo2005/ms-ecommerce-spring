package dev.luisoliveira.msproductmanagement.specifications;

import dev.luisoliveira.msproductmanagement.models.ProductModel;
import dev.luisoliveira.msproductmanagement.models.SubCategoryModel;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @Or({
            @Spec(path = "name", spec = Like.class),
            @Spec(path = "sku", spec = Equal.class),
            @Spec(path = "codeBar", spec = Equal.class)
    })
    public interface ProductSpec extends Specification<ProductModel> {}

    public static Specification<ProductModel> productSubCategoryId(final UUID subCategoryId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<ProductModel> product = root;
            Root<SubCategoryModel> subCategory = query.from(SubCategoryModel.class);
            Expression<Collection<ProductModel>> subCategoryProducts = subCategory.get("products");
            return cb.and(cb.equal(subCategory.get("subCategoryId"), subCategoryId), cb.isMember(product, subCategoryProducts));
        };
    }
}
