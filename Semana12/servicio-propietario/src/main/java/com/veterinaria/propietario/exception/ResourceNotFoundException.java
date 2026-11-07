package com.veterinaria.propietario.exception;

/**
 * Se lanza cuando no existe el recurso solicitado (HTTP 404).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
