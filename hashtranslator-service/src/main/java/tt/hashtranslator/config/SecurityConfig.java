package tt.hashtranslator.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import tt.authorization.Role;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ReactiveAuthenticationManager authManager;

    private static final String[] ENDPOINTS_WHITELIST = {
            "/login",
    };
    private static final String[] ADMIN_ENDPOINTS = {
            "/api/users/**",
            "/api/applications/**"
    };

    private static final String[] USER_ENDPOINTS = {
            "/api/applications/**",
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(httpBasicSpec -> httpBasicSpec.authenticationManager(authManager))
                .authorizeExchange(authorizeExchangeSpec -> {
                    authorizeExchangeSpec
                            .pathMatchers(ENDPOINTS_WHITELIST).permitAll()
                            .pathMatchers(USER_ENDPOINTS).hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                            .pathMatchers(ADMIN_ENDPOINTS).hasAuthority(Role.ADMIN.name())
                            .anyExchange()
                            .authenticated();
                })
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        return http.build();
    }
}
