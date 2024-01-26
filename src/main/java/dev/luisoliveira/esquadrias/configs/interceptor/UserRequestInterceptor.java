package dev.luisoliveira.esquadrias.configs.interceptor;

import dev.luisoliveira.esquadrias.models.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class UserRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private UserContextService userContextService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<UserModel> user = userContextService.getLoggedInUser();
        return true;
    }
}

