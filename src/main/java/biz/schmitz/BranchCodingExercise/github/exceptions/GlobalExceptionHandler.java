package biz.schmitz.BranchCodingExercise.github.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GithubUserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(GithubUserNotFoundException exception) {
        var message = exception.getMessage() != null ? exception.getMessage() : "Unable to get github user";
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", message));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(FeignException exception) {
        return ResponseEntity.status(exception.status())
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error has occurred.", "details", exception.getMessage()));
    }
}
