package com.duoc.mscitasmedicas.repository;

import com.duoc.mscitasmedicas.model.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<CitaMedica, Long> {

    List<CitaMedica> findByEstadoIgnoreCase(String estado);

    List<CitaMedica> findByFecha(LocalDate fecha);

    boolean existsByFechaAndHoraAndEstadoIgnoreCase(LocalDate fecha, String hora, String estado);

    boolean existsByFechaAndHoraAndEstadoIgnoreCaseAndIdNot(LocalDate fecha, String hora, String estado, Long id);
}