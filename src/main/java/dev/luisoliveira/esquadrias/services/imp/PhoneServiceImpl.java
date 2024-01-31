package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.PhoneModel;
import dev.luisoliveira.esquadrias.repositories.PhoneRepository;
import dev.luisoliveira.esquadrias.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    @Override
    public PhoneModel save(PhoneModel phone) {
        return phoneRepository.save(phone);
    }
    @Override
    public Optional<PhoneModel> findById(UUID phoneId) {
        return phoneRepository.findById(phoneId);
    }
}
