package tt.hashtranslator.service;

import reactor.core.publisher.Mono;
import tt.authorization.NewUserRequest;
import tt.authorization.UserDto;

public interface UserAuthService {
    Mono<UserDto> createNewUser(NewUserRequest newUserRequest);
}
