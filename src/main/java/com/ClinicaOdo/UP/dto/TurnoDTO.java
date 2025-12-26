package com.ClinicaOdo.UP.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TurnoDTO {
    private Long id;
    private LocalDate fecha;
    private String pacienteNombre;
    private String pacienteApellido;
    private String odontologoNombre;
    private String odontologoApellido;
}
