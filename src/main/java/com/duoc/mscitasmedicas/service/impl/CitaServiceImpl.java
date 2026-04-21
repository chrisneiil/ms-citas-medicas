package com.duoc.mscitasmedicas.service.impl;

import com.duoc.mscitasmedicas.dto.request.CitaRequestDto;
import com.duoc.mscitasmedicas.dto.response.CitaResponseDto;
import com.duoc.mscitasmedicas.dto.response.MensajeResponseDto;
import com.duoc.mscitasmedicas.model.CitaMedica;
import com.duoc.mscitasmedicas.repository.CitaRepository;
import com.duoc.mscitasmedicas.service.CitaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public List<CitaResponseDto> obtenerCitas() {
        return citaRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CitaResponseDto buscarPorId(Long id) {
        CitaMedica cita = citaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        return mapToResponseDto(cita);
    }

    @Override
    public List<CitaResponseDto> buscarPorEstado(String estado) {
        String estadoNormalizado = normalizarEstado(estado);

        return citaRepository.findByEstadoIgnoreCase(estadoNormalizado)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponseDto> buscarPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> consultarDisponibilidad(LocalDate fecha) {
        List<String> horariosBase = new ArrayList<>();
        horariosBase.add("08:00");
        horariosBase.add("08:30");
        horariosBase.add("09:00");
        horariosBase.add("09:30");
        horariosBase.add("10:00");
        horariosBase.add("10:30");
        horariosBase.add("11:00");
        horariosBase.add("11:30");
        horariosBase.add("12:00");

        List<CitaMedica> citasDelDia = citaRepository.findByFecha(fecha);

        for (CitaMedica cita : citasDelDia) {
            if ("PROGRAMADA".equalsIgnoreCase(cita.getEstado())) {
                horariosBase.remove(cita.getHora());
            }
        }

        return horariosBase;
    }

    @Override
    public MensajeResponseDto programarCita(CitaRequestDto nuevaCita) {
        String estadoNormalizado = normalizarEstado(nuevaCita.getEstado());

        if ("PROGRAMADA".equals(estadoNormalizado)
                && citaRepository.existsByFechaAndHoraAndEstadoIgnoreCase(
                nuevaCita.getFecha(),
                nuevaCita.getHora(),
                "PROGRAMADA"
        )) {
            throw new IllegalArgumentException("El horario ya está reservado");
        }

        CitaMedica cita = new CitaMedica();
        cita.setPaciente(nuevaCita.getPaciente().trim());
        cita.setMedico(nuevaCita.getMedico().trim());
        cita.setEspecialidad(nuevaCita.getEspecialidad().trim());
        cita.setFecha(nuevaCita.getFecha());
        cita.setHora(nuevaCita.getHora().trim());
        cita.setEstado(estadoNormalizado);
        cita.setMotivo(nuevaCita.getMotivo().trim());

        CitaMedica citaGuardada = citaRepository.save(cita);

        return new MensajeResponseDto("Cita programada correctamente con id: " + citaGuardada.getId());
    }

    @Override
    public MensajeResponseDto actualizarCita(Long id, CitaRequestDto citaActualizada) {
        CitaMedica cita = citaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        String estadoNormalizado = normalizarEstado(citaActualizada.getEstado());

        if ("PROGRAMADA".equals(estadoNormalizado)
                && citaRepository.existsByFechaAndHoraAndEstadoIgnoreCaseAndIdNot(
                citaActualizada.getFecha(),
                citaActualizada.getHora(),
                "PROGRAMADA",
                id
        )) {
            throw new IllegalArgumentException("El horario ya está reservado");
        }

        cita.setPaciente(citaActualizada.getPaciente().trim());
        cita.setMedico(citaActualizada.getMedico().trim());
        cita.setEspecialidad(citaActualizada.getEspecialidad().trim());
        cita.setFecha(citaActualizada.getFecha());
        cita.setHora(citaActualizada.getHora().trim());
        cita.setEstado(estadoNormalizado);
        cita.setMotivo(citaActualizada.getMotivo().trim());

        citaRepository.save(cita);

        return new MensajeResponseDto("Cita actualizada correctamente");
    }

    @Override
    public MensajeResponseDto cancelarCita(Long id) {
        CitaMedica cita = citaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(cita.getEstado())) {
            throw new IllegalArgumentException("La cita ya está cancelada");
        }

        cita.setEstado("CANCELADA");
        citaRepository.save(cita);

        return new MensajeResponseDto("Cita cancelada correctamente");
    }

    @Override
    public MensajeResponseDto eliminarCita(Long id) {
        CitaMedica cita = citaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada"));

        citaRepository.delete(cita);

        return new MensajeResponseDto("Cita eliminada correctamente");
    }

    private CitaResponseDto mapToResponseDto(CitaMedica cita) {
        return new CitaResponseDto(
                cita.getId(),
                cita.getPaciente(),
                cita.getMedico(),
                cita.getEspecialidad(),
                cita.getFecha(),
                cita.getHora(),
                cita.getEstado(),
                cita.getMotivo()
        );
    }

    private String normalizarEstado(String estado) {
        String valor = estado.trim().toUpperCase();

        if (!"PROGRAMADA".equals(valor) && !"CANCELADA".equals(valor)) {
            throw new IllegalArgumentException("El estado debe ser PROGRAMADA o CANCELADA");
        }

        return valor;
    }
}