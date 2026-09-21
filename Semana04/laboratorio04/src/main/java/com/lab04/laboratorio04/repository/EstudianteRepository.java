package com.lab04.laboratorio04.repository;

import com.lab04.laboratorio04.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}