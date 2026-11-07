package com.veterinaria.propietario.controller;

import com.veterinaria.propietario.dto.PropietarioRequest;
import com.veterinaria.propietario.dto.PropietarioResponse;
import com.veterinaria.propietario.services.PropietarioService;
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
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService service;

    public PropietarioController(PropietarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<PropietarioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PropietarioResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropietarioResponse crear(@Valid @RequestBody PropietarioRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public PropietarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody PropietarioRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
