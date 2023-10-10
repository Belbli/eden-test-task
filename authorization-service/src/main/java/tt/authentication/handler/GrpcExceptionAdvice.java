package tt.authentication.handler;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import tt.authentication.exception.ApplicationException;

@GrpcAdvice
public class GrpcExceptionAdvice {
    @GrpcExceptionHandler
    public Status handleInvalidArgument(ApplicationException e) {
        return e.getStatus().withDescription(e.getMessage()).withCause(e);
    }
}
