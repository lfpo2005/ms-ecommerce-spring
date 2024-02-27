package dev.luisoliveira.msshoppingcart.service;

import dev.luisoliveira.msshoppingcart.model.ProductCart;

import java.util.UUID;

public interface ProductCartService {
    ProductCart save(ProductCart productCart);

    void delete(UUID productId);
}
