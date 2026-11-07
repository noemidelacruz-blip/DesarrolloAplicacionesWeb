package com.veterinaria.mascota.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MascotaRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 60, message = "El nombre no puede superar los 60 caracteres")
        String nombre,

        @NotBlank(message = "La especie es obligatoria")
        @Size(max = 40, message = "La especie no puede superar los 40 caracteres")
        String especie,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
        LocalDate fechaNacimiento,

        @NotNull(message = "El idPropietario es obligatorio")
        @Positive(message = "El idPropietario debe ser un número positivo")
        Long idPropietario) {
}
