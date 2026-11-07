package com.veterinaria.propietario.repository;

import com.veterinaria.propietario.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);
}
