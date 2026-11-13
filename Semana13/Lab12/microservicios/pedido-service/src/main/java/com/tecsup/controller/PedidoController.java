package com.tecsup.controller;

import com.tecsup.model.EstadoPedido;
import com.tecsup.model.Pedido;
import com.tecsup.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Pedido obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@Valid @RequestBody Pedido p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(p));
    }

    @PutMapping("/{id}/atender")
    public Pedido atender(@PathVariable Long id) {
        return service.cambiarEstado(id, EstadoPedido.ATENDIDO);
    }

    @PutMapping("/{id}/anular")
    public Pedido anular(@PathVariable Long id) {
        return service.cambiarEstado(id, EstadoPedido.ANULADO);
    }
}
