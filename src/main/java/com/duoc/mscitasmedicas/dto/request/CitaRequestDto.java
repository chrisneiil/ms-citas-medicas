package com.duoc.mscitasmedicas.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CitaRequestDto {

    @NotBlank(message = "El paciente es obligatorio")
    @Size(max = 100, message = "El paciente no puede superar 100 caracteres")
    private String paciente;

    @NotBlank(message = "El médico es obligatorio")
    @Size(max = 100, message = "El médico no puede superar 100 caracteres")
    private String medico;


    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 100, message = "La especialidad no puede superar 100 caracteres")
    private String especialidad;

    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @NotBlank(message = "La hora es obligatoria")
    @Pattern(
            regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$",
            message = "La hora debe tener formato HH:mm"
    )
    private String hora;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "^(?i)(PROGRAMADA|CANCELADA)$",
            message = "El estado debe ser PROGRAMADA o CANCELADA"
    )
    private String estado;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 255, message = "El motivo no puede superar 255 caracteres")
    private String motivo;

    public CitaRequestDto() {
    }

}