package com.duoc.mscitasmedicas.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "CITA_MEDICA")
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cita_medica_seq_gen")
    @SequenceGenerator(
            name = "cita_medica_seq_gen",
            sequenceName = "SEQ_CITA_MEDICA",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "PACIENTE", nullable = false, length = 100)
    private String paciente;

    @Column(name = "MEDICO", nullable = false, length = 100)
    private String medico;

    @Column(name = "ESPECIALIDAD", nullable = false, length = 100)
    private String especialidad;

    @Column(name = "FECHA_CITA", nullable = false)
    private LocalDate fecha;

    @Column(name = "HORA_CITA", nullable = false, length = 5)
    private String hora;

    @Column(name = "ESTADO", nullable = false, length = 15)
    private String estado;

    @Column(name = "MOTIVO", nullable = false, length = 255)
    private String motivo;

    public CitaMedica() {
    }

    public CitaMedica(Long id, String paciente, String medico, String especialidad, LocalDate fecha, String hora, String estado, String motivo) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.motivo = motivo;
    }

    public Long getId() {
        return id;
    }

    public String getPaciente() {
        return paciente;
    }

    public String getMedico() {
        return medico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}