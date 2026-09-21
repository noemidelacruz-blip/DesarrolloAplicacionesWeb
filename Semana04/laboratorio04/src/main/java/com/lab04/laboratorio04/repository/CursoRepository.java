package com.lab04.laboratorio04.repository;

import com.lab04.laboratorio04.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}