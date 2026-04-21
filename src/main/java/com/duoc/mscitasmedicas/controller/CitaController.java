package com.duoc.mscitasmedicas.controller;

import com.duoc.mscitasmedicas.dto.request.CitaRequestDto;
import com.duoc.mscitasmedicas.dto.response.CitaResponseDto;
import com.duoc.mscitasmedicas.dto.response.MensajeResponseDto;
import com.duoc.mscitasmedicas.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaResponseDto>> listarCitas() {
        return ResponseEntity.ok(citaService.obtenerCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDto> obtenerCitaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.buscarPorId(id));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaResponseDto>> obtenerCitasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(citaService.buscarPorEstado(estado));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaResponseDto>> obtenerCitasPorFecha(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha
    ) {
        return ResponseEntity.ok(citaService.buscarPorFecha(fecha));
    }

    @GetMapping("/disponibilidad/{fecha}")
    public ResponseEntity<List<String>> consultarDisponibilidad(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha
    ) {
        return ResponseEntity.ok(citaService.consultarDisponibilidad(fecha));
    }

    @PostMapping
    public ResponseEntity<MensajeResponseDto> programarCita(@Valid @RequestBody CitaRequestDto nuevaCita) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.programarCita(nuevaCita));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeResponseDto> actualizarCita(
            @PathVariable Long id,
            @Valid @RequestBody CitaRequestDto citaActualizada
    ) {
        return ResponseEntity.ok(citaService.actualizarCita(id, citaActualizada));
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<MensajeResponseDto> cancelarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cancelarCita(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponseDto> eliminarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.eliminarCita(id));
    }
}