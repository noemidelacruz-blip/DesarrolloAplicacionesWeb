package com.veterinaria.consulta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ConsultaResponse(
        Long id,
        Long idMascota,
        LocalDate fecha,
        String motivo,
        String diagnostico,
        BigDecimal costo) {
}
