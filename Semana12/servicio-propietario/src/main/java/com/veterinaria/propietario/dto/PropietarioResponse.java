package com.veterinaria.propietario.dto;

public record PropietarioResponse(
        Long id,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String email) {
}
