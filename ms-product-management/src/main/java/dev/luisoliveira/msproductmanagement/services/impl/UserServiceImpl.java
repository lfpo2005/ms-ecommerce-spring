package dev.luisoliveira.msproductmanagement.services.impl;

import dev.luisoliveira.msproductmanagement.models.UserModel;
import dev.luisoliveira.msproductmanagement.repositories.UserRepository;
import dev.luisoliveira.msproductmanagement.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserModel save(UserModel userModel) {
        log.info("Saving new user {} to the database", userModel.getUserId());
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }
}
