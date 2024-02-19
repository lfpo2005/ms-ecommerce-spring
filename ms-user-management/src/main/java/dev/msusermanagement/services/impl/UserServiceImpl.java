package dev.msusermanagement.services.impl;


import dev.msusermanagement.configurations.kafka.UserProducer;
import dev.msusermanagement.dtos.UserEventDto;
import dev.msusermanagement.enums.ActionType;
import dev.msusermanagement.models.UserModel;
import dev.msusermanagement.repositories.UserRepository;
import dev.msusermanagement.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserProducer userProducer;

    @Override
    public List<UserModel> findAll() {

        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }
    @Transactional
    @Override
    public Optional<UserModel> findByIdWithAddressesAndPhones(UUID userId) {
        Optional<UserModel> userModelOptional = userRepository.findByIdWithAddressesAndPhones(userId);
        if (userModelOptional.isPresent()) {
            userModelOptional.get().getAddress().size();
            userModelOptional.get().getPhones().size();
        }
        return userModelOptional;
    }

    @Override
    public UserModel save(UserModel userModel) {
        userRepository.save(userModel);
        return userModel;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByFullName(String fullName) {
        return userRepository.existsByFullName(fullName);
    }

    @Transactional
    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }
    @Override
    public UserModel updateUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public boolean existsByCpf(String encryptedCpf) {
        return userRepository.existsByCpf(encryptedCpf);
    }

    @Transactional
    @Override
    public UserModel saveUser(UserModel userModel) {
       userModel = save(userModel);
       userProducer.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
       return userModel;
    }

    @Transactional
    @Override
    public UserModel deactivateUser(UserModel userModel) {
        userModel = save(userModel);
        var userEventDto = new UserEventDto();
        userEventDto.setUserId(userModel.getUserId());
        userEventDto.setActive(userModel.getActive());
        userEventDto.setDeleted(userModel.getDeleted());
        userEventDto.setUpdateAt(userModel.getUpdateAt().toString());
        userProducer.publishUserEvent(userEventDto, ActionType.DEACTIVATE);
        return userModel;
    }

    @Transactional
    @Override
    public UserModel saveActiveUser(UserModel userModel) {
        userModel = save(userModel);
        var userEventDto = new UserEventDto();
        userEventDto.setUserId(userModel.getUserId());
        userEventDto.setActive(userModel.getActive());
        userEventDto.setDeleted(userModel.getDeleted());
        userEventDto.setUpdateAt(userModel.getUpdateAt().toString());
        userProducer.publishUserEvent(userEventDto, ActionType.ACTIVE);
        return userModel;
    }

    @Transactional
    @Override
    public UserModel saveUpdateUser(UserModel userModel) {
        userModel = save(userModel);
        userProducer.publishUserEvent(userModel.convertToUserEventDto(), ActionType.UPDATE);
        return userModel;
    }
    @Transactional
    @Override
    public UserModel deactivateAndDeleteUser(UserModel userModel) {
        userModel = save(userModel);
        var userEventDto = new UserEventDto();
        userEventDto.setUserId(userModel.getUserId());
        userEventDto.setActive(userModel.getActive());
        userEventDto.setDeleted(userModel.getDeleted());
        userEventDto.setUpdateAt(userModel.getUpdateAt().toString());
        userProducer.publishUserEvent(userEventDto, ActionType.DELETE);
        return userModel;
    }

    @Override
    public UserModel updatePassword(UserModel userModel) {
        return save(userModel);
    }

}
