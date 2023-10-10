package tt.authentication.exception;

import io.grpc.Status;

public class UserAlreadyExistsException extends ApplicationException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public Status getStatus() {
        return Status.ALREADY_EXISTS;
    }
}
