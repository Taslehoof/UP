package com.ClinicaOdo.UP.repository;

import com.ClinicaOdo.UP.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    Optional<Turno> findByFecha(LocalDate fecha);
    List<Turno> findByPacienteId(Long id);
    List<Turno> findByOdontologoId(Long id);
}
