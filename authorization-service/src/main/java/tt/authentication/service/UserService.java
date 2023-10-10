package tt.authentication.service;

import tt.authorization.NewUserRequest;
import tt.authorization.UserDto;

import java.util.Optional;

public interface UserService {
    UserDto findAuthorizedUser(String email, String password);

    UserDto createNewUser(NewUserRequest newUserRequest);

    Optional<UserDto> findUserByEmail(String email);
}
