package tt.hashtranslator.config;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;
import tt.authorization.UserAuthenticationRequest;
import tt.authorization.UserAuthorizationServiceGrpc;
import tt.authorization.UserDto;

import java.util.List;

@Configuration
public class AuthManagerConfig {
    @GrpcClient("authorizationService")
    private UserAuthorizationServiceGrpc.UserAuthorizationServiceBlockingStub userAuthService;

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return auth -> {
            UserAuthenticationRequest authenticationRequest = UserAuthenticationRequest.newBuilder()
                    .setEmail(auth.getPrincipal().toString())
                    .setPassword(auth.getCredentials().toString())
                    .build();
            try {
                UserDto userDto = userAuthService.authorize(authenticationRequest);
                if (userDto != null) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDto.getId(), null,
                            List.of(new SimpleGrantedAuthority(userDto.getRole().name()))
                    );
                    SecurityContextHolder.getContext()
                            .setAuthentication(token);
                    return Mono.just(token);
                } else {
                    return Mono.empty();
                }
            } catch (Throwable ex) {
                return Mono.error(ex);
            }
        };

    }
}
