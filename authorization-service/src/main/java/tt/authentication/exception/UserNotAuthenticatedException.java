package tt.authentication.exception;

import io.grpc.Status;

public class UserNotAuthenticatedException extends ApplicationException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }

    @Override
    public Status getStatus() {
        return Status.UNAUTHENTICATED;
    }
}
