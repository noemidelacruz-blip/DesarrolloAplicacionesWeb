package com.veterinaria.consulta.controller;

import com.veterinaria.consulta.dto.ConsultaRequest;
import com.veterinaria.consulta.dto.ConsultaResponse;
import com.veterinaria.consulta.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConsultaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ConsultaResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @GetMapping("/mascota/{idMascota}")
    public List<ConsultaResponse> listarPorMascota(@PathVariable Long idMascota) {
        return service.listarPorMascota(idMascota);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse crear(@Valid @RequestBody ConsultaRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public ConsultaResponse actualizar(@PathVariable Long id, @Valid @RequestBody ConsultaRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
