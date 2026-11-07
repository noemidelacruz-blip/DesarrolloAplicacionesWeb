package com.veterinaria.consulta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;

    /** Referencia lógica (id) al servicio Mascota; no hay FK porque es otra base de datos. */
    @Column(name = "id_mascota", nullable = false)
    private Long idMascota;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "motivo", nullable = false, length = 200)
    private String motivo;

    @Column(name = "diagnostico", nullable = false, length = 500)
    private String diagnostico;

    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    public Consulta() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdMascota() { return idMascota; }
    public void setIdMascota(Long idMascota) { this.idMascota = idMascota; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
