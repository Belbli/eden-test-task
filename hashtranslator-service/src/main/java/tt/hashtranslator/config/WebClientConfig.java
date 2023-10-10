package tt.hashtranslator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${web-client.decrypt-apis.hash-decrypt.base-url:https://api.hash-decrypt.io}")
    private String hashDecryptBaseUrl;

    @Bean
    public WebClient hashDecryptWebClient() {
        return WebClient.builder()
                .baseUrl(hashDecryptBaseUrl)
                .build();
    }
}