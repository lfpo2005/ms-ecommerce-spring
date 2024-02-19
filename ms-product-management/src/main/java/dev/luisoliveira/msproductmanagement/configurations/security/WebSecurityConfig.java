package dev.luisoliveira.msproductmanagement.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public AuthenticationJwtFilter authenticationJwtFilter() {
        return new AuthenticationJwtFilter();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy =
                "ROLE_DEVELOPER > ROLE_ADMIN \n" +
                        "ROLE_ADMIN > ROLE_MANAGER \n" +
                        "ROLE_MANAGER > ROLE_SELLER \n" +
                        "ROLE_SELLER > ROLE_CUSTOMER \n" +
                        "ROLE_CUSTOMER > ROLE_EMPLOYEE \n" +
                        "ROLE_EMPLOYEE > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Add your custom JWT filter
        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                // Configure exception handling with custom entry point
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                // Define session management policy
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"))
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Expose-Headers", "Authorization"))
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
