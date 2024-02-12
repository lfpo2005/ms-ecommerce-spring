package dev.msusermanagement.services;


import dev.msusermanagement.models.AddressModel;

import java.util.Optional;
import java.util.UUID;

public interface AddressService {


    AddressModel save(AddressModel addressModel);

    Optional<AddressModel> findById(UUID addressId);
}
