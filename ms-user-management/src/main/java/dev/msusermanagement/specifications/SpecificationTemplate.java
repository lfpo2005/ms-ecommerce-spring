package dev.msusermanagement.specifications;


import dev.msusermanagement.models.UserModel;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

        @Or({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "active", spec = Equal.class)
        })
    public interface UserSpec extends Specification<UserModel> {
            public static Specification<UserModel> active(Boolean active) {
                return (root, query, criteriaBuilder) -> {
                    if (active == null) {
                        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                    }
                    return criteriaBuilder.equal(root.get("active"), active);
                };
            }

            public static Specification<UserModel> deleted(Boolean deleted) {
                return (root, query, criteriaBuilder) -> {
                    if (deleted == null) {
                        return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
                    }
                    return criteriaBuilder.equal(root.get("deleted"), deleted);
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