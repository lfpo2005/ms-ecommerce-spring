package dev.luisoliveira.esquadrias.controllers;

import dev.luisoliveira.esquadrias.configs.security.JwtProvider;
import dev.luisoliveira.esquadrias.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    public void registerUser_WithValidUser_ReturnsCreatedStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"fullName\": \"fullName novo\", \"password\": \"password\", \"email\": \"test@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void registerUser_WithExistingUsername_ReturnsConflictStatus() throws Exception {
        when(userService.existsByUsername("testuser")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"fullName\": \"fullName novo\", \"password\": \"password\", \"email\": \"test1@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void registerUser_WithExistingEmail_ReturnsConflictStatus() throws Exception {
        when(userService.existsByEmail("test@example.com")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser1\", \"fullName\": \"fullName novo\", \"password\": \"password\", \"email\": \"test@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @WithMockUser
    public void authenticateUser_WithValidCredentials_ReturnsJwtToken() throws Exception {
        // Mock do AuthenticationManager para retornar uma autenticação válida
        when(authenticationManager.authenticate(any()))
                .thenReturn(new TestingAuthenticationToken("testuser", "123123", "ROLE_USER"));

        // Mock do JwtProvider para retornar um token de teste
        when(jwtProvider.generateJwt(any(Authentication.class))).thenReturn("dummy-jwt-token");

        // Execução do teste: realizando uma requisição POST para o endpoint de login
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password\"}"))
                // Verifica se a resposta é 200 OK
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Verifica se o token existe
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

}
