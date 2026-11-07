package com.veterinaria.mascota.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manejo centralizado de errores: toda respuesta de error sigue el formato ErrorResponse.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 - recurso inexistente
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(cuerpo(HttpStatus.NOT_FOUND, ex.getMessage(), request, null));
    }

    // 422 - la referencia (id) a otro microservicio no existe
    @ExceptionHandler(ReferenciaInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleReferencia(ReferenciaInvalidaException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(cuerpo(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request, null));
    }

    // 503 - el otro microservicio no responde
    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ResponseEntity<ErrorResponse> handleNoDisponible(ServicioNoDisponibleException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(cuerpo(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), request, null));
    }


    // 409 - restricción de integridad (ej. valor único duplicado)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrity(DataIntegrityViolationException ex, WebRequest request) {
        log.warn("Violación de integridad: {}", ex.getMostSpecificCause().getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(cuerpo(HttpStatus.CONFLICT, "La operación viola una restricción de integridad de los datos", request, null));
    }

    // 500 - cualquier error no previsto (sin exponer detalles internos)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, WebRequest request) {
        log.error("Error inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(cuerpo(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno en el servidor", request, null));
    }

    // 400 - falla de @Valid en el DTO: devuelve campo -> mensaje
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> errores.putIfAbsent(fe.getField(), fe.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(cuerpo(HttpStatus.BAD_REQUEST, "Hay campos inválidos en la solicitud", request, errores));
    }

    // 400 - JSON mal formado o tipo/fecha con formato incorrecto
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String msg = "El cuerpo de la solicitud es inválido o tiene un formato incorrecto "
                + "(verifique el JSON y que las fechas usen el formato yyyy-MM-dd)";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(cuerpo(HttpStatus.BAD_REQUEST, msg, request, null));
    }

    // 400 - parámetro de ruta con tipo incorrecto (ej. /api/xxx/abc)
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status,
                                                        WebRequest request) {
        String esperado = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "otro tipo";
        String msg = "El valor '" + ex.getValue() + "' no es válido; se esperaba un valor de tipo " + esperado;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(cuerpo(HttpStatus.BAD_REQUEST, msg, request, null));
    }

    private ErrorResponse cuerpo(HttpStatus status, String mensaje, WebRequest request, Map<String, String> errores) {
        String path = request.getDescription(false).replace("uri=", "");
        return new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensaje, path, errores);
    }
}
