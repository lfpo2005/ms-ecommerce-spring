package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.CommissionModel;
import dev.luisoliveira.esquadrias.repositories.CommissionRepository;
import dev.luisoliveira.esquadrias.services.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommissionServiceImpl implements CommissionService {

    @Autowired
    CommissionRepository commissionRepository;

    @Override
    public boolean existsByNameCostsAndUser_UserId(String name, UUID userId) {
        return commissionRepository.existsByNameAndUser_UserId(name, userId);
    }

    @Override
    public CommissionModel save(CommissionModel commissionModel) {
        return commissionRepository.save(commissionModel);
    }

    @Override
    public List<CommissionModel> findAllByUserId(UUID userId) {
        return commissionRepository.findAllByUser_UserId(userId);
    }

    @Override
    public Optional<CommissionModel> findById(UUID commissionId) {
        return commissionRepository.findById(commissionId);
    }

    @Override
    public void delete(CommissionModel fixedCost) {
        commissionRepository.delete(fixedCost);
    }
}
