package com.tecsup.services;

import com.tecsup.model.Cliente;
import com.tecsup.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Optional<Cliente> obtener(Long id) {
        return repo.findById(id);
    }

    public Cliente guardar(Cliente c) {
        return repo.save(c);
    }

    public Optional<Cliente> actualizar(Long id, Cliente datos) {
        return repo.findById(id).map(c -> {
            c.setNombre(datos.getNombre());
            c.setApellido(datos.getApellido());
            c.setDireccion(datos.getDireccion());
            c.setEstado(datos.getEstado());
            c.setNroDni(datos.getNroDni());
            return repo.save(c);
        });
    }

    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
