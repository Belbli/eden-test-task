package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tt.hashtranslator.model.ApplicationRequest;
import tt.hashtranslator.model.HashResult;
import tt.hashtranslator.service.ApplicationProcessingService;
import tt.hashtranslator.service.ApplicationService;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThirdPartyApplicationProcessingService implements ApplicationProcessingService {
    public static final String DECRYPT_API_URI = "/v1/md5/";
    private Logger logger = LoggerFactory.getLogger(ThirdPartyApplicationProcessingService.class);

    private final ApplicationService applicationService;
    private final WebClient hashDecryptWebClient;

    //curl -X GET https://api.hash-decrypt.io/v1/md5/5f4dcc3b5aa765d61d8327deb882cf99
    @Override
    public Mono<UUID> processApplication(ApplicationRequest applicationRequest) {
        return applicationService.saveApplication()
                .doOnNext(applicationId -> Flux.fromIterable(applicationRequest.hashes()).delayElements(Duration.ofSeconds(2))
                        .flatMap(hash -> hashDecryptWebClient.get()
                                .uri(DECRYPT_API_URI + hash)
                                .exchangeToMono(clientResponse -> {
                                    if (clientResponse.statusCode().is2xxSuccessful()) {
                                        return clientResponse.bodyToMono(String.class)
                                                .map(result -> new HashResult(hash, result));
                                    } else {
                                        return Mono.just(new HashResult(hash, "EMPTY"));
                                    }
                                })
                        )
                        .collectList()
                        .flatMap(results -> applicationService.updateApplication(applicationId, results))
                        .subscribe()
                );
    }
}
