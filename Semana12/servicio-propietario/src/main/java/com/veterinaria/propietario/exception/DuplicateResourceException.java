package com.veterinaria.propietario.exception;

/**
 * Se lanza cuando se intenta registrar un dato que debe ser único (HTTP 409).
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
