package tt.hashtranslator.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tt.hashtranslator.model.Application;

import java.util.UUID;

public interface ApplicationRepository extends ReactiveCrudRepository<Application, UUID> {
    Flux<Application> findApplicationsByUserId(UUID userId);
}
