package tt.authentication.exception;

import io.grpc.Status;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public Status getStatus() {
        return Status.INVALID_ARGUMENT;
    }
}
