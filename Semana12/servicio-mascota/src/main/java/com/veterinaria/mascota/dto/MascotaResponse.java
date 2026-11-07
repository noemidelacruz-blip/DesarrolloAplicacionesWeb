package com.veterinaria.mascota.dto;

import java.time.LocalDate;

public record MascotaResponse(
        Long id,
        String nombre,
        String especie,
        LocalDate fechaNacimiento,
        Long idPropietario) {
}
