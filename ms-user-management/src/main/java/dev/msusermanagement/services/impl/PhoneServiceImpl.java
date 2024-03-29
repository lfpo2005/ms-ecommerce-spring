package dev.msusermanagement.services.impl;


import dev.msusermanagement.models.PhoneModel;
import dev.msusermanagement.repositories.PhoneRepository;
import dev.msusermanagement.services.PhoneService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    @Transactional
    @Override
    public PhoneModel save(PhoneModel phone) {
        return phoneRepository.save(phone);
    }
    @Override
    public Optional<PhoneModel> findById(UUID phoneId) {
        return phoneRepository.findById(phoneId);
    }
}
