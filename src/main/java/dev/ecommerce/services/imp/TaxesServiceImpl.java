package dev.ecommerce.services.imp;

import dev.ecommerce.models.TaxesModel;
import dev.ecommerce.repositories.TaxesRepository;
import dev.ecommerce.services.TaxesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaxesServiceImpl implements TaxesService {

    @Autowired
    TaxesRepository taxesRepository;

    @Override
    public boolean existsByNameCostsAndUser_UserId(String name, UUID userId) {
        return taxesRepository.existsByNameAndUser_UserId(name, userId);
    }
    @Override
    public TaxesModel save(TaxesModel taxesModel) {
        return taxesRepository.save(taxesModel);
    }

    @Override
    public List<TaxesModel> findAllByUserId(UUID userId) {
        return taxesRepository.findAllByUser_UserId(userId);
    }

    @Override
    public Optional<TaxesModel> findById(UUID taxesId) {
        return taxesRepository.findById(taxesId);
    }

    @Override
    public void delete(TaxesModel taxesModel) {
        taxesRepository.delete(taxesModel);
    }
}
