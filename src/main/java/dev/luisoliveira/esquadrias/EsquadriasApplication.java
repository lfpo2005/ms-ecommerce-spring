package dev.luisoliveira.esquadrias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("dev.luisoliveira.esquadrias.models")
public class EsquadriasApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsquadriasApplication.class, args);
    }

}
