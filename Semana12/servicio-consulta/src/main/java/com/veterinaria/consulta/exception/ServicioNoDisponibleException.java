package com.veterinaria.consulta.exception;

/**
 * El microservicio remoto no está disponible (HTTP 503).
 */
public class ServicioNoDisponibleException extends RuntimeException {

    public ServicioNoDisponibleException(String message) {
        super(message);
    }
}
