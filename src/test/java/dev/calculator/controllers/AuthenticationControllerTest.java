package dev.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ecommerce.configs.security.JwtProvider;
import dev.ecommerce.dtos.LoginDto;
import dev.ecommerce.dtos.UserDto;
import dev.ecommerce.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void registerUser_WithValidUserAndNonExistingUsernameEmailAndFullName_ReturnsCreatedStatus() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("newuser");
        userDto.setEmail("newuser@example.com");
        userDto.setFullName("New User");
        userDto.setPassword("password");
        userDto.setCpf("350.015.810-28");
        userDto.setBirthDate("01-01-2000");

        when(userService.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userService.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userService.existsByFullName(userDto.getFullName())).thenReturn(false);
        //when(userService.isValidBirthDate(userDto.getBirthDate())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void registerUser_WithInvalidBirthDateFormat_ReturnsBadRequestStatus() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("newuser");
        userDto.setEmail("newuser@example.com");
        userDto.setFullName("New User");
        userDto.setPassword("password");
        userDto.setBirthDate("2000-01-01");

        when(userService.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userService.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userService.existsByFullName(userDto.getFullName())).thenReturn(false);
        //when(userService.isValidBirthDate(userDto.getBirthDate())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void registerUser_WithExistingUsername_ReturnsConflictStatus() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("existinguser");
        userDto.setEmail("newuser@example.com");
        userDto.setFullName("New User");
        userDto.setPassword("password");
        userDto.setBirthDate("01-01-2000");

        when(userService.existsByUsername(userDto.getUsername())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void registerUser_WithExistingEmail_ReturnsConflictStatus() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("newuser");
        userDto.setEmail("existinguser@example.com");
        userDto.setFullName("New User");
        userDto.setPassword("password");
        userDto.setBirthDate("01-01-2000");

        when(userService.existsByEmail(userDto.getEmail())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void registerUser_WithExistingFullName_ReturnsConflictStatus() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("newuser");
        userDto.setEmail("newuser@example.com");
        userDto.setFullName("Existing User");
        userDto.setPassword("password");
        userDto.setBirthDate("01-01-2000");

        when(userService.existsByFullName(userDto.getFullName())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void authenticateUser_WithValidCredentials_ReturnsJwtDto() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("existinguser");
        loginDto.setPassword("password");

        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        when(jwtProvider.generateJwt(any())).thenReturn("jwtToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("jwtToken"));
    }

    @Test
    public void logout_WithAuthenticatedUser_ReturnsOkStatus() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("existinguser", "password"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void logout_WithUnauthenticatedUser_ReturnsOkStatus() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void index_ReturnsExpectedMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Logging Spring Boot..."));
    }
}

