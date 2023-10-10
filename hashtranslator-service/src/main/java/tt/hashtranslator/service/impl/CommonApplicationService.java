package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tt.hashtranslator.exception.ApplicationNotFoundException;
import tt.hashtranslator.exception.InsufficientPermissionException;
import tt.hashtranslator.model.Application;
import tt.hashtranslator.model.HashResult;
import tt.hashtranslator.repository.ApplicationRepository;
import tt.hashtranslator.service.ApplicationService;
import tt.hashtranslator.util.SecurityHolderUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommonApplicationService implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    public Flux<Application> findUserApplications() {
        return SecurityHolderUtil.getAuthenticatedUserId()
                .flatMapMany(applicationRepository::findApplicationsByUserId);
    }

    @Override
    public Mono<Application> findUserApplicationById(UUID applicationId) {
        return SecurityHolderUtil.getAuthenticatedUserId()
                .flatMap(userId -> applicationRepository.findById(applicationId)
                        .switchIfEmpty(Mono.error(new ApplicationNotFoundException("Application with id " + applicationId + " not found.")))
                        .filter(application -> application.getUserId().equals(userId))
                        .switchIfEmpty(Mono.error(new InsufficientPermissionException("User doesn't have access to the application.")))
                );
    }

    @Override
    public Mono<UUID> saveApplication() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication().getPrincipal().toString())
                .map(UUID::fromString)
                .flatMap(userId -> {
                    UUID applicationId = UUID.randomUUID();
                    Application application = new Application(applicationId, userId, Collections.emptyList());
                    return applicationRepository.save(application);
                })
                .map(Application::getId);
    }

    @Override
    public Mono<Application> updateApplication(UUID applicationId, List<HashResult> results) {
        return applicationRepository.findById(applicationId)
                .map(application -> {
                    application.getHashResults().addAll(results);
                    return application;
                })
                .flatMap(applicationRepository::save);

    }
}
