package tt.authentication.mapper;

import tt.authorization.UserDto;
import tt.authentication.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.newBuilder()
                .setId(user.getId().toString())
                .setEmail(user.getEmail())
                .setRole(user.getRole())
                .build();
    }
}
