package com.lab04.laboratorio04.controllers;

import com.lab04.laboratorio04.models.Usuario;
import com.lab04.laboratorio04.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. Registrar usuario con perfil
    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    // 2. Listar usuarios con su perfil
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    // 3. Obtener usuario por ID
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    // 4. Eliminar usuario (en cascada elimina su perfil)
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}