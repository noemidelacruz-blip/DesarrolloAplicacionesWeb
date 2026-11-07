package com.veterinaria.consulta.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ConsultaRequest(

        @NotNull(message = "El idMascota es obligatorio")
        @Positive(message = "El idMascota debe ser un número positivo")
        Long idMascota,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotBlank(message = "El motivo es obligatorio")
        @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
        String motivo,

        @NotBlank(message = "El diagnóstico es obligatorio")
        @Size(max = 500, message = "El diagnóstico no puede superar los 500 caracteres")
        String diagnostico,

        @NotNull(message = "El costo es obligatorio")
        @DecimalMin(value = "0.00", message = "El costo no puede ser negativo")
        @Digits(integer = 8, fraction = 2, message = "El costo admite hasta 8 enteros y 2 decimales")
        BigDecimal costo) {
}
