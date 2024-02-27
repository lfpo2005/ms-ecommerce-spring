package dev.luisoliveira.msshoppingcart.repositories;

import dev.luisoliveira.msshoppingcart.model.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCartRepository extends JpaRepository<ProductCart, UUID> {

}
