package dev.luisoliveira.esquadrias.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private Environment env;


    public static final String[] LIST_CORS_URL = {
            "http://localhost:4200"

    };

    private static final String[] AUTH_WHITELIST = {
            "/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/v3/api-docs.yaml",
            "/v3/api-docs.yaml/**"
    };

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
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowedOrigins = Arrays.asList(LIST_CORS_URL);
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                );

        // Define authorization requests
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
        );

        /*        http.authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers(AUTH_WHITELIST).permitAll();
            if (env.acceptsProfiles(Profiles.of("dev"))) {
                authorize.anyRequest().permitAll();
            } else {
                authorize.anyRequest().authenticated();
            }
        });*/

        // Disable CSRF (if using JWT or if CSRF is not needed)
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
