package dev.luisoliveira.storejava.services.impl;

import dev.luisoliveira.storejava.models.PhoneModel;
import dev.luisoliveira.storejava.repositories.PhoneRepository;
import dev.luisoliveira.storejava.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    @Override
    public PhoneModel save(PhoneModel phone) {
        return phoneRepository.save(phone);
    }
}
