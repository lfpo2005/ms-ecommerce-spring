package dev.luisoliveira.esquadrias.services.imp;


import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.repositories.UserRepository;
import dev.luisoliveira.esquadrias.services.UserService;
import dev.luisoliveira.esquadrias.utils.CryptoUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        userRepository.save(userModel);
        return userModel;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByFullName(String fullName) {
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


    @Override
    public boolean isValidBirthDate(String birthDate) {
        String datePattern = "^\\d{2}-\\d{2}-\\d{4}$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(birthDate.trim());
        return matcher.matches();
    }

}
