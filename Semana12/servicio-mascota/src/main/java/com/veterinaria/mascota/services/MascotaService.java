package com.veterinaria.mascota.services;

import com.veterinaria.mascota.dto.MascotaRequest;
import com.veterinaria.mascota.dto.MascotaResponse;
import com.veterinaria.mascota.exception.ResourceNotFoundException;
import com.veterinaria.mascota.model.Mascota;
import com.veterinaria.mascota.repository.MascotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository repository;
    private final PropietarioClient propietarioClient;

    public MascotaService(MascotaRepository repository, PropietarioClient propietarioClient) {
        this.repository = repository;
        this.propietarioClient = propietarioClient;
    }

    @Transactional(readOnly = true)
    public List<MascotaResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MascotaResponse obtener(Long id) {
        return toResponse(buscar(id));
    }

    @Transactional(readOnly = true)
    public List<MascotaResponse> listarPorPropietario(Long idPropietario) {
        return repository.findByIdPropietario(idPropietario).stream().map(this::toResponse).toList();
    }

    @Transactional
    public MascotaResponse crear(MascotaRequest request) {
        propietarioClient.verificarExiste(request.idPropietario());
        Mascota mascota = new Mascota();
        aplicar(mascota, request);
        return toResponse(repository.save(mascota));
    }

    @Transactional
    public MascotaResponse actualizar(Long id, MascotaRequest request) {
        Mascota mascota = buscar(id);
        propietarioClient.verificarExiste(request.idPropietario());
        aplicar(mascota, request);
        return toResponse(repository.save(mascota));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    // ---------- helpers ----------
    private Mascota buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una mascota con id " + id));
    }

    private void aplicar(Mascota m, MascotaRequest r) {
        m.setNombre(r.nombre().trim());
        m.setEspecie(r.especie().trim());
        m.setFechaNacimiento(r.fechaNacimiento());
        m.setIdPropietario(r.idPropietario());
    }

    private MascotaResponse toResponse(Mascota m) {
        return new MascotaResponse(m.getId(), m.getNombre(), m.getEspecie(),
                m.getFechaNacimiento(), m.getIdPropietario());
    }
}
