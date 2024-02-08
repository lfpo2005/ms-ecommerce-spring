package dev.ecommerce.services;


import dev.ecommerce.models.PhoneModel;

import java.util.Optional;
import java.util.UUID;

public interface PhoneService {

    PhoneModel save(PhoneModel phone);

    Optional<PhoneModel> findById(UUID phoneId);
}
