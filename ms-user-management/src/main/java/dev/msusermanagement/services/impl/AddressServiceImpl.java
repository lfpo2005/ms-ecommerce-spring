package dev.msusermanagement.services.impl;

import dev.msusermanagement.models.AddressModel;
import dev.msusermanagement.repositories.AddressRepository;
import dev.msusermanagement.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public AddressModel save(AddressModel addressModel) {
                addressRepository.save(addressModel);
                return addressModel;

    }

    @Override
    public Optional<AddressModel> findById(UUID addressId) {
        return addressRepository.findById(addressId);
    }
}
