package com.veterinaria.propietario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PropietarioRequest(

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 80, message = "Los nombres no pueden superar los 80 caracteres")
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 80, message = "Los apellidos no pueden superar los 80 caracteres")
        String apellidos,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
        String dni,

        @Pattern(regexp = "\\d{7,15}", message = "El teléfono debe tener entre 7 y 15 dígitos")
        String telefono,

        @Email(message = "El email no tiene un formato válido")
        @Size(max = 100, message = "El email no puede superar los 100 caracteres")
        String email) {
}
