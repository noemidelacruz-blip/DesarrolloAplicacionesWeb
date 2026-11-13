package com.tecsup.services;

import com.tecsup.model.Categoria;
import com.tecsup.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Optional<Categoria> obtener(Long id) {
        return repo.findById(id);
    }

    public Categoria guardar(Categoria c) {
        return repo.save(c);
    }

    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
