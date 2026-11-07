package com.veterinaria.mascota.exception;

/**
 * El id enviado referencia un recurso que no existe en otro microservicio (HTTP 422).
 */
public class ReferenciaInvalidaException extends RuntimeException {

    public ReferenciaInvalidaException(String message) {
        super(message);
    }
}
