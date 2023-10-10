package tt.hashtranslator.handler;

import io.grpc.StatusRuntimeException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerRequest;
import tt.hashtranslator.exception.ApplicationException;
import tt.hashtranslator.exception.ApplicationNotFoundException;
import tt.hashtranslator.exception.InsufficientPermissionException;
import tt.hashtranslator.exception.NotAuthenticatedUserException;

import java.util.Map;
import java.util.TreeMap;

@Order(0)
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler
    public ResponseEntity<Object> handle(StatusRuntimeException ex) {
        Map<String, Object> body = new TreeMap<>();
        body.put("message", ex.getMessage());
        body.put("exception", ex.getClass().getName());
        body.put("status", ex.getStatus());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(ApplicationException ex, ServerHttpRequest request) {
        return ResponseEntity.badRequest().body(buildResponse(ex, request));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(InsufficientPermissionException ex, ServerHttpRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildResponse(ex, request));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(NotAuthenticatedUserException ex, ServerHttpRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildResponse(ex, request));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(ApplicationNotFoundException ex, ServerHttpRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildResponse(ex, request));
    }

    private Map<String, Object> buildResponse(ApplicationException ex, ServerHttpRequest request) {
        Map<String, Object> body = new TreeMap<>();

        body.put("message", ex.getMessage());
        body.put("exception", ex.getClass().getName());
        body.put("path", request.getPath().value());


        return body;
    }
}
