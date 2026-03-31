package com.duoc.mscitasmedicas.service;

import com.duoc.mscitasmedicas.model.CitaMedica;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitaService {

    private List<CitaMedica> citas = new ArrayList<>();

    public CitaService() {
        citas.add(new CitaMedica(1, "Ana Torres", "Dr. Pérez", "Medicina General", "2026-04-10", "09:00", "PROGRAMADA", "Control general"));
        citas.add(new CitaMedica(2, "Luis Soto", "Dra. Ramírez", "Pediatría", "2026-04-10", "10:00", "PROGRAMADA", "Fiebre"));
        citas.add(new CitaMedica(3, "Camila Díaz", "Dr. Muñoz", "Dermatología", "2026-04-11", "11:00", "PROGRAMADA", "Revisión de piel"));
        citas.add(new CitaMedica(4, "Pedro Rojas", "Dra. Silva", "Traumatología", "2026-04-11", "12:00", "CANCELADA", "Dolor de rodilla"));
        citas.add(new CitaMedica(5, "María León", "Dr. Pérez", "Medicina General", "2026-04-12", "09:30", "PROGRAMADA", "Chequeo"));
        citas.add(new CitaMedica(6, "Diego Ramos", "Dr. Torres", "Cardiología", "2026-04-12", "10:30", "PROGRAMADA", "Control presión"));
        citas.add(new CitaMedica(7, "Fernanda Cruz", "Dra. Vega", "Ginecología", "2026-04-13", "08:30", "PROGRAMADA", "Control anual"));
        citas.add(new CitaMedica(8, "Javiera Soto", "Dr. Muñoz", "Dermatología", "2026-04-13", "11:30", "PROGRAMADA", "Alergia"));
    }

    public List<CitaMedica> obtenerCitas() {
        return citas;
    }

    public CitaMedica buscarPorId(int id) {
        for (CitaMedica cita : citas) {
            if (cita.getId() == id) {
                return cita;
            }
        }
        return null;
    }

    public List<CitaMedica> buscarPorEstado(String estado) {
        List<CitaMedica> resultado = new ArrayList<>();

        for (CitaMedica cita : citas) {
            if (cita.getEstado().equalsIgnoreCase(estado)) {
                resultado.add(cita);
            }
        }

        return resultado;
    }

    public List<CitaMedica> buscarPorFecha(String fecha) {
        List<CitaMedica> resultado = new ArrayList<>();

        for (CitaMedica cita : citas) {
            if (cita.getFecha().equals(fecha)) {
                resultado.add(cita);
            }
        }

        return resultado;
    }

    public List<String> consultarDisponibilidad(String fecha) {
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

        for (CitaMedica cita : citas) {
            if (cita.getFecha().equals(fecha) && cita.getEstado().equalsIgnoreCase("PROGRAMADA")) {
                horariosBase.remove(cita.getHora());
            }
        }

        return horariosBase;
    }

    public String programarCita(CitaMedica nuevaCita) {
        if (nuevaCita.getId() <= 0) {
            return "Error: el id debe ser mayor a 0";
        }

        if (buscarPorId(nuevaCita.getId()) != null) {
            return "Error: ya existe una cita con ese id";
        }

        if (nuevaCita.getPaciente() == null || nuevaCita.getPaciente().trim().isEmpty()) {
            return "Error: el paciente no puede estar vacío";
        }

        if (nuevaCita.getMedico() == null || nuevaCita.getMedico().trim().isEmpty()) {
            return "Error: el médico no puede estar vacío";
        }

        if (nuevaCita.getEspecialidad() == null || nuevaCita.getEspecialidad().trim().isEmpty()) {
            return "Error: la especialidad no puede estar vacía";
        }

        if (nuevaCita.getFecha() == null || nuevaCita.getFecha().trim().isEmpty()) {
            return "Error: la fecha no puede estar vacía";
        }

        if (nuevaCita.getHora() == null || nuevaCita.getHora().trim().isEmpty()) {
            return "Error: la hora no puede estar vacía";
        }

        if (nuevaCita.getMotivo() == null || nuevaCita.getMotivo().trim().isEmpty()) {
            return "Error: el motivo no puede estar vacío";
        }

        if (!nuevaCita.getEstado().equalsIgnoreCase("PROGRAMADA")
                && !nuevaCita.getEstado().equalsIgnoreCase("CANCELADA")) {
            return "Error: estado inválido";
        }

        for (CitaMedica cita : citas) {
            if (cita.getFecha().equals(nuevaCita.getFecha())
                    && cita.getHora().equals(nuevaCita.getHora())
                    && cita.getEstado().equalsIgnoreCase("PROGRAMADA")) {
                return "Error: el horario ya está reservado";
            }
        }

        citas.add(nuevaCita);
        return "Cita programada correctamente";
    }

    public String actualizarCita(int id, CitaMedica citaActualizada) {
        CitaMedica citaEncontrada = buscarPorId(id);

        if (citaEncontrada == null) {
            return "Error: cita no encontrada";
        }

        if (citaActualizada.getPaciente() == null || citaActualizada.getPaciente().trim().isEmpty()) {
            return "Error: el paciente no puede estar vacío";
        }

        if (citaActualizada.getMedico() == null || citaActualizada.getMedico().trim().isEmpty()) {
            return "Error: el médico no puede estar vacío";
        }

        if (citaActualizada.getEspecialidad() == null || citaActualizada.getEspecialidad().trim().isEmpty()) {
            return "Error: la especialidad no puede estar vacía";
        }

        if (citaActualizada.getFecha() == null || citaActualizada.getFecha().trim().isEmpty()) {
            return "Error: la fecha no puede estar vacía";
        }

        if (citaActualizada.getHora() == null || citaActualizada.getHora().trim().isEmpty()) {
            return "Error: la hora no puede estar vacía";
        }

        if (citaActualizada.getMotivo() == null || citaActualizada.getMotivo().trim().isEmpty()) {
            return "Error: el motivo no puede estar vacío";
        }

        if (!citaActualizada.getEstado().equalsIgnoreCase("PROGRAMADA")
                && !citaActualizada.getEstado().equalsIgnoreCase("CANCELADA")) {
            return "Error: estado inválido";
        }

        for (CitaMedica cita : citas) {
            if (cita.getId() != id
                    && cita.getFecha().equals(citaActualizada.getFecha())
                    && cita.getHora().equals(citaActualizada.getHora())
                    && cita.getEstado().equalsIgnoreCase("PROGRAMADA")) {
                return "Error: el horario ya está reservado";
            }
        }

        citaEncontrada.setPaciente(citaActualizada.getPaciente());
        citaEncontrada.setMedico(citaActualizada.getMedico());
        citaEncontrada.setEspecialidad(citaActualizada.getEspecialidad());
        citaEncontrada.setFecha(citaActualizada.getFecha());
        citaEncontrada.setHora(citaActualizada.getHora());
        citaEncontrada.setEstado(citaActualizada.getEstado());
        citaEncontrada.setMotivo(citaActualizada.getMotivo());

        return "Cita actualizada correctamente";
    }

    public String cancelarCita(int id) {
        CitaMedica citaEncontrada = buscarPorId(id);

        if (citaEncontrada == null) {
            return "Error: cita no encontrada";
        }

        if (citaEncontrada.getEstado().equalsIgnoreCase("CANCELADA")) {
            return "Error: la cita ya estaba cancelada";
        }

        citaEncontrada.setEstado("CANCELADA");
        return "Cita cancelada correctamente";
    }

    public String eliminarCita(int id) {
        CitaMedica citaEncontrada = buscarPorId(id);

        if (citaEncontrada == null) {
            return "Error: cita no encontrada";
        }

        citas.remove(citaEncontrada);
        return "Cita eliminada correctamente";
    }
}