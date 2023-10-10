package tt.authentication.exception;

import io.grpc.Status;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public Status getStatus() {
        return Status.NOT_FOUND;
    }
}
