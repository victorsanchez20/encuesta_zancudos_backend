package backend.controller;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Convierte las excepciones del servicio en respuestas 4xx con un mensaje
 * legible, en lugar de dejar que salgan como 500.
 *
 * El frontend muestra el campo "mensaje" del cuerpo de la respuesta.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private record ApiError(String mensaje) {
    }

    /** Datos inválidos detectados por el servicio (reglas de negocio). */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
    }

    /** JSON mal formado, enum desconocido o campo con tipo incompatible. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> cuerpoIlegible(HttpMessageNotReadableException e) {
        log.debug("Cuerpo de la petición ilegible: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new ApiError("Los datos enviados no tienen el formato esperado"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validacionFallida(MethodArgumentNotValidException e) {
        String detalle = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(new ApiError(detalle.isEmpty() ? "Datos inválidos" : detalle));
    }

    /** Violación de restricción en base de datos (por ejemplo, DNI duplicado). */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> conflictoDeDatos(DataIntegrityViolationException e) {
        log.debug("Violación de integridad: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("Ya existe un registro con esos datos"));
    }

    /**
     * Cualquier otro fallo se registra entero en el servidor, pero al cliente
     * solo se le devuelve un mensaje genérico: no se filtran stack traces.
     *
     * Las excepciones propias de Spring (404, 405, 415...) implementan
     * ErrorResponse y traen su propio código, así que se respeta en lugar de
     * convertirlas todas en 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> errorInesperado(Exception e) {

        if (e instanceof ErrorResponse errorResponse) {
            log.debug("Petición rechazada por Spring: {}", e.getMessage());
            return ResponseEntity.status(errorResponse.getStatusCode())
                    .body(new ApiError("La petición no se pudo procesar"));
        }

        log.error("Error no controlado en la API", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Ocurrió un error inesperado en el servidor"));
    }
}
