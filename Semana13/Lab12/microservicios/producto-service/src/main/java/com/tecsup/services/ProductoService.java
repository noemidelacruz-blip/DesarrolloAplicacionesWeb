package com.tecsup.services;

import com.tecsup.client.CategoriaClient;
import com.tecsup.dto.CategoriaDTO;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    @Autowired
    private CategoriaClient categoriaClient;

    public List<Producto> listar() {
        List<Producto> productos = repo.findAll();
        productos.forEach(p -> p.setCategoria(categoriaClient.obtenerCategoria(p.getCategoriaId())));
        return productos;
    }

    public Producto guardar(Producto p) {
        CategoriaDTO categoria;
        try {
            categoria = categoriaClient.obtenerCategoria(p.getCategoriaId());
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria no existe");
        }
        Producto producto = repo.save(p);
        producto.setCategoria(categoria);
        return producto;
    }

    public Producto obtener(Long id) {
        Producto producto = repo.findById(id).orElse(null);
        if (producto != null) {
            CategoriaDTO categoria = categoriaClient.obtenerCategoria(producto.getCategoriaId());
            producto.setCategoria(categoria);
        }
        return producto;
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
