package com.lab04.laboratorio04.controllers;

import com.lab04.laboratorio04.models.Curso;
import com.lab04.laboratorio04.models.Estudiante;
import com.lab04.laboratorio04.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    // 1. Crear estudiante con cursos
    @PostMapping
    public Estudiante crear(@RequestBody Estudiante estudiante) {
        return estudianteService.guardar(estudiante);
    }

    // Método de apoyo para crear cursos en BD
    @PostMapping("/curso")
    public Curso crearCurso(@RequestBody Curso curso) {
        return estudianteService.guardarCurso(curso);
    }

    // 2. Listar estudiantes con sus cursos
    @GetMapping
    public List<Estudiante> listarTodos() {
        return estudianteService.listarTodos();
    }

    // 3. Agregar curso a estudiante existente
    @PostMapping("/{estudianteId}/cursos/{cursoId}")
    public Estudiante agregarCurso(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        return estudianteService.agregarCursoAEstudiante(estudianteId, cursoId);
    }

    // 4. Quitar cursos
    @DeleteMapping("/{estudianteId}/cursos/{cursoId}")
    public Estudiante quitarCurso(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        return estudianteService.quitarCurso(estudianteId, cursoId);
    }

    // 5. Listar cursos de un estudiante
    @GetMapping("/{estudianteId}/cursos")
    public Set<Curso> listarCursos(@PathVariable Long estudianteId) {
        return estudianteService.listarCursosDeEstudiante(estudianteId);
    }
}