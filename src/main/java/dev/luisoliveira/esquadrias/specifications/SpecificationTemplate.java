package dev.luisoliveira.esquadrias.specifications;


import dev.luisoliveira.esquadrias.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

        @Or({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "active", spec = Equal.class)
        })
    public interface UserSpec extends Specification<UserModel> {
            public static Specification<UserModel> isActive(Boolean isActive) {
                return (root, query, criteriaBuilder) -> {
                    if (isActive == null) {
                        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                    }
                    return criteriaBuilder.equal(root.get("isActive"), isActive);
                };
            }

            public static Specification<UserModel> isDeleted(Boolean isDeleted) {
                return (root, query, criteriaBuilder) -> {
                    if (isDeleted == null) {
                        return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
                    }
                    return criteriaBuilder.equal(root.get("isDeleted"), isDeleted);
                };
            }

            public static Specification<UserModel> email(String email) {
                return (root, query, criteriaBuilder) -> {
                    if (email == null) {
                        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                    }
                    return criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), email);
                };
            }

            public static Specification<UserModel> fullName(String fullName) {
                return (root, query, criteriaBuilder) -> {
                    if (fullName == null) {
                        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                    }
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%");
                };
            }
        }
}