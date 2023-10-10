package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tt.authorization.NewUserRequest;
import tt.authorization.UserAuthorizationServiceGrpc;
import tt.authorization.UserDto;
import tt.hashtranslator.service.UserAuthService;


@Service
public class GrpcUserAuthService implements UserAuthService {
    @GrpcClient("authorizationService")
    private UserAuthorizationServiceGrpc.UserAuthorizationServiceBlockingStub userAuthService;

    @Override
    public Mono<UserDto> createNewUser(NewUserRequest newUserRequest) {
        return Mono.fromSupplier(() -> userAuthService.createNewUser(newUserRequest));
    }
}
