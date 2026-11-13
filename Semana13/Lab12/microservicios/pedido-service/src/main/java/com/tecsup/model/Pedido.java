package com.tecsup.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tecsup.dto.ClienteDTO;
import com.tecsup.dto.ProductoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "clienteId es obligatorio")
    private Long clienteId;

    @NotNull(message = "productoId es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    // Datos completados por Feign, no se guardan en la BD de pedido
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClienteDTO cliente;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductoDTO producto;
}
