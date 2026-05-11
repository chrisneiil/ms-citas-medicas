package com.duoc.mscitasmedicas.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
public class CitaResponseDto extends RepresentationModel<CitaResponseDto> {

    private Long id;
    private String paciente;
    private String medico;
    private String especialidad;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private String hora;
    private String estado;
    private String motivo;

    public CitaResponseDto() {
    }

    public CitaResponseDto(Long id, String paciente, String medico, String especialidad, LocalDate fecha, String hora, String estado, String motivo) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.motivo = motivo;
    }
}