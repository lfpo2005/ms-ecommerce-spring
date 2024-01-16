package dev.luisoliveira.esquadrias;

import dev.luisoliveira.esquadrias.controllers.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@WebMvcTest(AuthenticationController.class)

class EsquadriasApplicationTests {

    @Test
    void contextLoads() {
    }

}
