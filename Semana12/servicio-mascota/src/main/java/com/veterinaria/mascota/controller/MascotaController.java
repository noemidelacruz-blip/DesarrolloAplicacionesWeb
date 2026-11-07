package com.veterinaria.mascota.controller;

import com.veterinaria.mascota.dto.MascotaRequest;
import com.veterinaria.mascota.dto.MascotaResponse;
import com.veterinaria.mascota.services.MascotaService;
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
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService service;

    public MascotaController(MascotaService service) {
        this.service = service;
    }

    @GetMapping
    public List<MascotaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MascotaResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @GetMapping("/propietario/{idPropietario}")
    public List<MascotaResponse> listarPorPropietario(@PathVariable Long idPropietario) {
        return service.listarPorPropietario(idPropietario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MascotaResponse crear(@Valid @RequestBody MascotaRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public MascotaResponse actualizar(@PathVariable Long id, @Valid @RequestBody MascotaRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
