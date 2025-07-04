package app.scaffolding.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Validated
@RestControllerAdvice
public class GlobalExceptionHandler {

    //handler para excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknownException(Exception exception) {
        ApiError error = buildApiError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // Handler para validaciones fallidas en objetos @RequestBody (DTOs) validados con @Valid
    // Se dispara cuando un campo de un DTO no cumple con las anotaciones como @NotBlank, @Size, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException exception) {
        StringBuilder validationMessages = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            validationMessages.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        });
        ApiError apiError = ApiError.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(validationMessages.toString())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    // Handler para validaciones fallidas en parámetros simples (@PathVariable, @RequestParam, etc.)
    // Requiere que la clase (lo uso en el controller) esté anotada con @Validated para aplicar restricciones como @Min, @Max, etc.
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage()).collect(Collectors.joining("; "));

        ApiError error = ApiError.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    //handler para excepciones por JSON mal formado, campo faltante sin @NotNull, tipos incorrectos, etc (no se puede deserializar)
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJsonBody(HttpMessageNotReadableException ex) {
        ApiError error = buildApiError(ex, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //handler para excepciones por entidades no existentes
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundResource(ResourceNotFoundException exception) {
        ApiError error = buildApiError(exception, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    private ApiError buildApiError(Exception exception, HttpStatus httpStatus) {
        return ApiError.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(httpStatus.getReasonPhrase())
                .status(httpStatus.value())
                .message(exception.getMessage())
                .build();
    }
}
