package dev.luisoliveira.esquadrias.specifications;


import dev.luisoliveira.esquadrias.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {
        public static Specification<UserModel> isActive(Boolean isActive) {
            return (root, query, criteriaBuilder) -> {
                if (isActive == null) {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
                return criteriaBuilder.equal(root.get("active"), isActive);
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
    }




}