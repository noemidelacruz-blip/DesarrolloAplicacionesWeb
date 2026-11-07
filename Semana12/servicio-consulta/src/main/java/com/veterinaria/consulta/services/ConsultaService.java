package com.veterinaria.consulta.services;

import com.veterinaria.consulta.dto.ConsultaRequest;
import com.veterinaria.consulta.dto.ConsultaResponse;
import com.veterinaria.consulta.exception.ResourceNotFoundException;
import com.veterinaria.consulta.model.Consulta;
import com.veterinaria.consulta.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;
    private final MascotaClient mascotaClient;

    public ConsultaService(ConsultaRepository repository, MascotaClient mascotaClient) {
        this.repository = repository;
        this.mascotaClient = mascotaClient;
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ConsultaResponse obtener(Long id) {
        return toResponse(buscar(id));
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorMascota(Long idMascota) {
        return repository.findByIdMascotaOrderByFechaDesc(idMascota).stream().map(this::toResponse).toList();
    }

    @Transactional
    public ConsultaResponse crear(ConsultaRequest request) {
        mascotaClient.verificarExiste(request.idMascota());
        Consulta consulta = new Consulta();
        aplicar(consulta, request);
        return toResponse(repository.save(consulta));
    }

    @Transactional
    public ConsultaResponse actualizar(Long id, ConsultaRequest request) {
        Consulta consulta = buscar(id);
        mascotaClient.verificarExiste(request.idMascota());
        aplicar(consulta, request);
        return toResponse(repository.save(consulta));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    // ---------- helpers ----------
    private Consulta buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una consulta con id " + id));
    }

    private void aplicar(Consulta c, ConsultaRequest r) {
        c.setIdMascota(r.idMascota());
        c.setFecha(r.fecha());
        c.setMotivo(r.motivo().trim());
        c.setDiagnostico(r.diagnostico().trim());
        c.setCosto(r.costo());
    }

    private ConsultaResponse toResponse(Consulta c) {
        return new ConsultaResponse(c.getId(), c.getIdMascota(), c.getFecha(),
                c.getMotivo(), c.getDiagnostico(), c.getCosto());
    }
}
