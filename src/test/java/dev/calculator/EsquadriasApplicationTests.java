package dev.ecommerce;

import dev.ecommerce.controllers.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@WebMvcTest(AuthenticationController.class)

class EcommerceApplicationTests {

    @Test
    void contextLoads() {
    }

}
