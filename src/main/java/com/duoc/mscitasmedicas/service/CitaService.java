package com.duoc.mscitasmedicas.service;

import com.duoc.mscitasmedicas.dto.request.CitaRequestDto;
import com.duoc.mscitasmedicas.dto.response.CitaResponseDto;
import com.duoc.mscitasmedicas.dto.response.MensajeResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface CitaService {

    List<CitaResponseDto> obtenerCitas();

    CitaResponseDto buscarPorId(Long id);

    List<CitaResponseDto> buscarPorEstado(String estado);

    List<CitaResponseDto> buscarPorFecha(LocalDate fecha);

    List<String> consultarDisponibilidad(LocalDate fecha);

    MensajeResponseDto programarCita(CitaRequestDto nuevaCita);

    MensajeResponseDto actualizarCita(Long id, CitaRequestDto citaActualizada);

    MensajeResponseDto cancelarCita(Long id);

    MensajeResponseDto eliminarCita(Long id);
}