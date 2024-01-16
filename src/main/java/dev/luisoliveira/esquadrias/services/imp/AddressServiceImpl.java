package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.AddressModel;
import dev.luisoliveira.esquadrias.repositories.AddressRepository;
import dev.luisoliveira.esquadrias.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public AddressModel save(AddressModel addressModel) {
                addressRepository.save(addressModel);
                return addressModel;

    }
}
