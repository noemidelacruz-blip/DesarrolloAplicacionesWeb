package com.veterinaria.consulta.repository;

import com.veterinaria.consulta.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByIdMascotaOrderByFechaDesc(Long idMascota);
}
