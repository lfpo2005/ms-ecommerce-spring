package dev.luisoliveira.esquadrias.services.imp;


import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.repositories.UserRepository;
import dev.luisoliveira.esquadrias.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

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

/*
    @Override
    public Optional<UserModel> findByIdWithAddressesAndPhones(UUID userId) {
        return userRepository.findByIdWithAddressesAndPhones(userId);
    }
*/

    @Transactional
    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
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
    public UserModel updatePassword(UserModel userModel) {
        return save(userModel);
    }


}
