package tt.authentication.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import tt.authentication.service.UserService;
import tt.authorization.NewUserRequest;
import tt.authorization.UserAuthenticationRequest;
import tt.authorization.UserAuthorizationServiceGrpc;
import tt.authorization.UserDto;

@GrpcService
@RequiredArgsConstructor
public class UserAuthorizationService extends UserAuthorizationServiceGrpc.UserAuthorizationServiceImplBase {
    private final UserService userService;

    @Override
    public void authorize(UserAuthenticationRequest request, StreamObserver<UserDto> responseObserver) {
        UserDto userDto = userService.findAuthorizedUser(request.getEmail(), request.getPassword());

        responseObserver.onNext(userDto);
        responseObserver.onCompleted();
    }

    @Override
    public void createNewUser(NewUserRequest request, StreamObserver<UserDto> responseObserver) {
        UserDto newUser = userService.createNewUser(request);

        responseObserver.onNext(newUser);
        responseObserver.onCompleted();
    }
}
