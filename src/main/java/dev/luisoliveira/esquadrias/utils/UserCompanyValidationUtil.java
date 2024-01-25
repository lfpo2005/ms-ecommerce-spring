package dev.luisoliveira.esquadrias.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dev.luisoliveira.esquadrias.configs.security.UserDetailsImpl;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.UserModel;
import dev.luisoliveira.esquadrias.services.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
@Component
public class UserCompanyValidationUtil {

    private final UserService userService;

    @Autowired
    public UserCompanyValidationUtil(UserService userService) {
        this.userService = userService;
    }

    public Optional<CompanyModel> validateUserAndCompany() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            log.error("Unauthenticated user");
            return Optional.empty();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.trace("Logged in user: {}", userDetails.getUsername());

        Optional<UserModel> userOptional = userService.findById(userDetails.getUserId());
        if (!userOptional.isPresent()) {
            log.error("User not found");
            return Optional.empty();
        }
        UserModel user = userOptional.get();
        CompanyModel company = user.getCompany();

        if (company == null) {
            log.error("Company not found for user");
            return Optional.empty();
        }

        return Optional.of(company);
    }
}