package com.veterinaria.propietario.services;

import com.veterinaria.propietario.dto.PropietarioRequest;
import com.veterinaria.propietario.dto.PropietarioResponse;
import com.veterinaria.propietario.exception.DuplicateResourceException;
import com.veterinaria.propietario.exception.ResourceNotFoundException;
import com.veterinaria.propietario.model.Propietario;
import com.veterinaria.propietario.repository.PropietarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropietarioService {

    private final PropietarioRepository repository;

    public PropietarioService(PropietarioRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PropietarioResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PropietarioResponse obtener(Long id) {
        return toResponse(buscar(id));
    }

    @Transactional
    public PropietarioResponse crear(PropietarioRequest request) {
        if (repository.existsByDni(request.dni())) {
            throw new DuplicateResourceException("Ya existe un propietario con el DNI " + request.dni());
        }
        Propietario propietario = new Propietario();
        aplicar(propietario, request);
        return toResponse(repository.save(propietario));
    }

    @Transactional
    public PropietarioResponse actualizar(Long id, PropietarioRequest request) {
        Propietario propietario = buscar(id);
        if (repository.existsByDniAndIdNot(request.dni(), id)) {
            throw new DuplicateResourceException("Ya existe otro propietario con el DNI " + request.dni());
        }
        aplicar(propietario, request);
        return toResponse(repository.save(propietario));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    // ---------- helpers ----------
    private Propietario buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un propietario con id " + id));
    }

    private void aplicar(Propietario p, PropietarioRequest r) {
        p.setNombres(r.nombres().trim());
        p.setApellidos(r.apellidos().trim());
        p.setDni(r.dni());
        p.setTelefono(r.telefono());
        p.setEmail(r.email());
    }

    private PropietarioResponse toResponse(Propietario p) {
        return new PropietarioResponse(p.getId(), p.getNombres(), p.getApellidos(),
                p.getDni(), p.getTelefono(), p.getEmail());
    }
}
