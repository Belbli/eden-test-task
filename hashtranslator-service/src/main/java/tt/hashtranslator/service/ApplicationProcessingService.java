package tt.hashtranslator.service;

import reactor.core.publisher.Mono;
import tt.hashtranslator.model.ApplicationRequest;

import java.util.UUID;

public interface ApplicationProcessingService {
    Mono<UUID> processApplication(ApplicationRequest application);
}
