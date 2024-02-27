package dev.luisoliveira.msshoppingcart.service.impl;

import dev.luisoliveira.msshoppingcart.model.ProductCart;
import dev.luisoliveira.msshoppingcart.repositories.ProductCartRepository;
import dev.luisoliveira.msshoppingcart.service.ProductCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCartServiceImpl implements ProductCartService {


    @Autowired
    ProductCartRepository productCartRepository;


    @Override
    public ProductCart save(ProductCart productCart) {
        return productCartRepository.save(productCart);
    }

    @Override
    public void delete(UUID productId) {
        productCartRepository.deleteById(productId);

    }
}
