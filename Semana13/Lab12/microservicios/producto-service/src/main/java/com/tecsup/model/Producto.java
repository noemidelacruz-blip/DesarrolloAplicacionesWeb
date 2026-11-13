package com.tecsup.model;

import com.tecsup.dto.CategoriaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero")
    private Double precio;

    private int stock;

    @NotNull(message = "categoriaId es obligatorio")
    private Long categoriaId;

    // Se completa con Feign, no se guarda en la BD de producto
    @Transient
    private CategoriaDTO categoria;
}
