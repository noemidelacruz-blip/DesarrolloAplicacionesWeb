package com.lab04.laboratorio04.repository;

import com.lab04.laboratorio04.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}