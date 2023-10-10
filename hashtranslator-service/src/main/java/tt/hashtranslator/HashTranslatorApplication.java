package tt.hashtranslator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableWebFluxSecurity
@SpringBootApplication
public class HashTranslatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HashTranslatorApplication.class, args);
    }
}
