package tt.hashtranslator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tt.authorization.NewUserRequest;
import tt.authorization.UserDto;
import tt.hashtranslator.service.UserAuthService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserAuthService userAuthService;

    @PostMapping
    public Mono<UserDto> createNewUser(@RequestBody NewUserRequest newUserRequest) {
        return userAuthService.createNewUser(newUserRequest);
    }
}
