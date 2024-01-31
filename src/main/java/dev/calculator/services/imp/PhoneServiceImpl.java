package dev.calculator.services.imp;

import dev.calculator.repositories.PhoneRepository;
import dev.calculator.models.PhoneModel;
import dev.calculator.services.PhoneService;
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
