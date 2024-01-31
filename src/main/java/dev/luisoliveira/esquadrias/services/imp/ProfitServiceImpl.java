package dev.luisoliveira.esquadrias.services.imp;

import dev.luisoliveira.esquadrias.models.ProfitModel;
import dev.luisoliveira.esquadrias.repositories.ProfitRepository;
import dev.luisoliveira.esquadrias.services.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfitServiceImpl implements ProfitService {

    @Autowired
    ProfitRepository profitRepository;

    @Override
    public boolean existsByNameCostsAndUser_UserId(String name, UUID userId) {
        return profitRepository.existsByNameAndUser_UserId(name, userId);
    }
    @Override
    public ProfitModel save(ProfitModel profitModel) {
        return profitRepository.save(profitModel);
    }

    @Override
    public List<ProfitModel> findAllByUserId(UUID userId) {
        return profitRepository.findAllByUser_UserId(userId);
    }

    @Override
    public Optional<ProfitModel> findById(UUID profitId) {
        return profitRepository.findById(profitId);
    }

    @Override
    public void delete(ProfitModel fixedCost) {
        profitRepository.delete(fixedCost);
    }
}
