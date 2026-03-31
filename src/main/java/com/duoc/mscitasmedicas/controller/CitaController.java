package com.duoc.mscitasmedicas.controller;

import com.duoc.mscitasmedicas.model.CitaMedica;
import com.duoc.mscitasmedicas.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaMedica>> listarCitas() {
        return ResponseEntity.ok(citaService.obtenerCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCitaPorId(@PathVariable int id) {
        CitaMedica cita = citaService.buscarPorId(id);

        if (cita == null) {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Cita no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        return ResponseEntity.ok(cita);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaMedica>> obtenerCitasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(citaService.buscarPorEstado(estado));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaMedica>> obtenerCitasPorFecha(@PathVariable String fecha) {
        return ResponseEntity.ok(citaService.buscarPorFecha(fecha));
    }

    @GetMapping("/disponibilidad/{fecha}")
    public ResponseEntity<List<String>> consultarDisponibilidad(@PathVariable String fecha) {
        return ResponseEntity.ok(citaService.consultarDisponibilidad(fecha));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> programarCita(@RequestBody CitaMedica nuevaCita) {
        String resultado = citaService.programarCita(nuevaCita);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", resultado);

        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> actualizarCita(@PathVariable int id, @RequestBody CitaMedica citaActualizada) {
        String resultado = citaService.actualizarCita(id, citaActualizada);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", resultado);

        if (resultado.equals("Error: cita no encontrada")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Map<String, String>> cancelarCita(@PathVariable int id) {
        String resultado = citaService.cancelarCita(id);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", resultado);

        if (resultado.equals("Error: cita no encontrada")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarCita(@PathVariable int id) {
        String resultado = citaService.eliminarCita(id);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", resultado);

        if (resultado.equals("Error: cita no encontrada")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        return ResponseEntity.ok(respuesta);
    }
}