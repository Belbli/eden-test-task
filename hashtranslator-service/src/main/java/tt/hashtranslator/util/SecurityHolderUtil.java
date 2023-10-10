package tt.hashtranslator.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class SecurityHolderUtil {
    public static Mono<UUID> getAuthenticatedUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("reactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .switchIfEmpty(Mono.error(new IllegalStateException("authentication is empty")))
                .map(Authentication::getPrincipal)
                .switchIfEmpty(Mono.error(new IllegalStateException("principal is empty")))
                .map(Object::toString)
                .map(UUID::fromString);
    }
}
