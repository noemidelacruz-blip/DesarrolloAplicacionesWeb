package com.lab04.laboratorio04.service;

import com.lab04.laboratorio04.models.Curso;
import com.lab04.laboratorio04.models.Estudiante;
import com.lab04.laboratorio04.repository.CursoRepository;
import com.lab04.laboratorio04.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // 1. Crear estudiante con cursos
    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // 2. Listar estudiantes con sus cursos
    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    // Método auxiliar para crear cursos sueltos
    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    // 3. Agregar curso a estudiante existente (con validación para no repetir)
    public Estudiante agregarCursoAEstudiante(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar que no se repita la inscripción
        if (estudiante.getCursos().contains(curso)) {
            throw new RuntimeException("El estudiante ya está inscrito en este curso");
        }

        estudiante.getCursos().add(curso);
        return estudianteRepository.save(estudiante);
    }

    // 4. Quitar curso de estudiante
    public Estudiante quitarCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        estudiante.getCursos().remove(curso);
        return estudianteRepository.save(estudiante);
    }

    // 5. Listar cursos de un estudiante
    public Set<Curso> listarCursosDeEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return estudiante.getCursos();
    }
}