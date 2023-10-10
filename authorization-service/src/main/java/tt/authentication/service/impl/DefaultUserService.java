package tt.authentication.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tt.authentication.exception.UserAlreadyExistsException;
import tt.authentication.mapper.UserMapper;
import tt.authentication.model.User;
import tt.authentication.repository.UserRepository;
import tt.authentication.service.UserService;
import tt.authorization.NewUserRequest;
import tt.authorization.Role;
import tt.authorization.UserDto;
import tt.authentication.exception.BadCredentialsException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "User with email=%s not found";
    private static final int COST = 6;
    public static final String BAD_CREDENTIALS_MESSAGE = "check your credentials";
    private final UserRepository userRepository;

    @Override
    public UserDto findAuthorizedUser(String email, String password) {

        return userRepository.findByEmail(email)
                .filter(user -> BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray()).verified)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS_MESSAGE));

    }

    @Override
    public UserDto createNewUser(NewUserRequest newUserRequest) {
        findUserByEmail(newUserRequest.getEmail())
                .ifPresent(userDto -> {
                    throw new UserAlreadyExistsException("user with this email is already registered.");
                });

        String passwordHash = BCrypt.withDefaults().hashToString(COST, newUserRequest.getPassword().toCharArray());
        User newUser = new User(UUID.randomUUID(), newUserRequest.getEmail(), passwordHash, Role.USER);

        User saved = userRepository.save(newUser);
        return UserMapper.toDto(saved);
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDto);
    }

}
