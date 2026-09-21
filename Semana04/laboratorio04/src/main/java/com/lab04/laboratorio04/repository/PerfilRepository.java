package com.lab04.laboratorio04.repository;

import com.lab04.laboratorio04.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}