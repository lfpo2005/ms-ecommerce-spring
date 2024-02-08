package dev.ecommerce.controllers;

import dev.ecommerce.configs.security.UserDetailsImpl;
import dev.ecommerce.exceptions.CalculationException;
import dev.ecommerce.models.CalculatorSumModel;
import dev.ecommerce.services.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerTest {

    @InjectMocks
    private CalculatorController calculatorController;

    @Mock
    private CalculatorService calculatorService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return OK status when getAllSumAllValues is called with valid authentication")
    void shouldReturnOkWhenGetAllSumAllValuesCalledWithValidAuthentication() throws CalculationException {
        UUID userId = UUID.randomUUID();
        String username = "testUsername";
        String email = "testEmail";
        String password = "testPassword";
        String fullName = "testFullName";
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        UserDetailsImpl userDetails = new UserDetailsImpl(userId, username, email, password, fullName, authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        CalculatorSumModel calculatorSumModel = new CalculatorSumModel();
        calculatorSumModel.setTotalMonthly(new BigDecimal(100.0));
        when(calculatorService.totalMonthly(userId)).thenReturn(calculatorSumModel);

        ResponseEntity<Object> response = calculatorController.getAllSumAllValues(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(calculatorSumModel, response.getBody());
    }

}