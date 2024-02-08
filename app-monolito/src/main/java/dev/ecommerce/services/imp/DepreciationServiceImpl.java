package dev.ecommerce.services.imp;

import dev.ecommerce.repositories.DepreciationRepository;
import dev.ecommerce.models.DepreciationModel;
import dev.ecommerce.services.DepreciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepreciationServiceImpl implements DepreciationService {

    @Autowired
    DepreciationRepository depreciationRepository;

    @Override
    public DepreciationModel save(DepreciationModel depreciation) {
        depreciationRepository.save(depreciation);
        return depreciation;
    }
    @Override
    public Object findAll() {
        return depreciationRepository.findAll();
    }

    @Override
    public List<DepreciationModel> findAllByUserId(UUID userId) {
        return depreciationRepository.findAllByUser_UserId(userId);
    }

    @Override
    public boolean existsByEquipmentAndUserId(String equipment, UUID userId) {
        return depreciationRepository.existsByEquipmentAndUser_UserId(equipment, userId);
    }

    @Override
    public Optional<DepreciationModel> findById(UUID depreciationId) {
        return depreciationRepository.findById(depreciationId);
    }
}
