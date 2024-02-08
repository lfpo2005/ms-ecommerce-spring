package dev.ecommerce.configs.interceptor;

import dev.ecommerce.configs.security.UserDetailsImpl;
import dev.ecommerce.models.UserModel;
import dev.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserContextService {

    @Autowired
    UserRepository userRepository;

    // Este método retorna o usuário logado (UserModel) do contexto de segurança
    public Optional<UserModel> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated user");

        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var userModel = userRepository.findById(userDetails.getUserId());

        return userModel;
    }
}

