package tt.hashtranslator.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tt.hashtranslator.model.Application;
import tt.hashtranslator.model.HashResult;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    Flux<Application> findUserApplications();

    Mono<Application> findUserApplicationById(UUID applicationId);
    Mono<UUID> saveApplication();

    Mono<Application> updateApplication(UUID applicationId, List<HashResult> processedHashes);
}
