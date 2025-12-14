package com.ClinicaOdo.UP.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Turno {

    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fecha;

    public Turno (Paciente paciente, Odontologo odontologo, LocalDate fecha) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
    }
}
