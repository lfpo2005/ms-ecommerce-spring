package dev.calculator.services.imp;

import dev.calculator.models.AddressModel;
import dev.calculator.repositories.AddressRepository;
import dev.calculator.services.AddressService;
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
