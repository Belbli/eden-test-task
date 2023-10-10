package tt.hashtranslator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tt.hashtranslator.model.Application;
import tt.hashtranslator.model.ApplicationRequest;
import tt.hashtranslator.service.ApplicationProcessingService;
import tt.hashtranslator.service.ApplicationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ApplicationProcessingService applicationProcessor;

    @GetMapping
    public Flux<Application> getApplications() {
        return applicationService.findUserApplications();
    }

    @GetMapping("/{applicationId}")
    public Mono<Application> getApplicationById(@PathVariable UUID applicationId) {
        return applicationService.findUserApplicationById(applicationId);
    }

    @PostMapping
    public Mono<UUID> processApplication(@RequestBody ApplicationRequest applicationRequest) {
        //return Mono.empty();
        return applicationProcessor.processApplication(applicationRequest);
    }

}
