package com.duoc.mscitasmedicas.service.impl;


import com.duoc.mscitasmedicas.dto.request.CitaRequestDto;
import com.duoc.mscitasmedicas.dto.response.CitaResponseDto;
import com.duoc.mscitasmedicas.dto.response.MensajeResponseDto;
import com.duoc.mscitasmedicas.model.CitaMedica;
import com.duoc.mscitasmedicas.repository.CitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CitaServiceImplTest {

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaServiceImpl citaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaObtenerCitas() {
        CitaMedica cita = new CitaMedica();
        cita.setId(1L);
        cita.setPaciente("Ana Torres");
        cita.setMedico("Dr. Pérez");
        cita.setEspecialidad("Medicina General");
        cita.setFecha(LocalDate.of(2026, 4, 10));
        cita.setHora("09:00");
        cita.setEstado("PROGRAMADA");
        cita.setMotivo("Control");

        when(citaRepository.findAll()).thenReturn(List.of(cita));

        List<CitaResponseDto> resultado = citaService.obtenerCitas();

        assertEquals(1, resultado.size());
        assertEquals("Ana Torres", resultado.get(0).getPaciente());
        verify(citaRepository).findAll();
    }

    @Test
    void deberiaBuscarCitaPorId() {
        CitaMedica cita = new CitaMedica();
        cita.setId(1L);
        cita.setPaciente("Ana Torres");
        cita.setMedico("Dr. Pérez");
        cita.setEspecialidad("Medicina General");
        cita.setFecha(LocalDate.of(2026, 4, 10));
        cita.setHora("09:00");
        cita.setEstado("PROGRAMADA");
        cita.setMotivo("Control");

        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        CitaResponseDto resultado = citaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Ana Torres", resultado.getPaciente());
        assertEquals("PROGRAMADA", resultado.getEstado());
        verify(citaRepository).findById(1L);
    }

    @Test
    void deberiaProgramarCita() {
        CitaRequestDto request = new CitaRequestDto();
        request.setPaciente("Carlos López");
        request.setMedico("Dr. Pérez");
        request.setEspecialidad("Medicina General");
        request.setFecha(LocalDate.of(2026, 4, 10));
        request.setHora("08:00");
        request.setEstado("PROGRAMADA");
        request.setMotivo("Control");

        CitaMedica guardada = new CitaMedica();
        guardada.setId(9L);
        guardada.setPaciente("Carlos López");
        guardada.setMedico("Dr. Pérez");
        guardada.setEspecialidad("Medicina General");
        guardada.setFecha(LocalDate.of(2026, 4, 10));
        guardada.setHora("08:00");
        guardada.setEstado("PROGRAMADA");
        guardada.setMotivo("Control");

        when(citaRepository.existsByFechaAndHoraAndEstadoIgnoreCase(
                request.getFecha(), request.getHora(), "PROGRAMADA"
        )).thenReturn(false);

        when(citaRepository.save(any(CitaMedica.class))).thenReturn(guardada);

        MensajeResponseDto resultado = citaService.programarCita(request);

        assertNotNull(resultado);
        assertEquals("Cita programada correctamente con id: 9", resultado.getMensaje());
        verify(citaRepository).save(any(CitaMedica.class));
    }

    @Test
    void deberiaLanzarNoSuchElementExceptionCuandoNoExisteCita() {
        when(citaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> citaService.buscarPorId(99L));

        verify(citaRepository).findById(99L);
    }
}