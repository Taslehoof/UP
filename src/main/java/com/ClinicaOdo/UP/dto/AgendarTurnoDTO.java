package com.ClinicaOdo.UP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgendarTurnoDTO {
    private Long pacienteId;
    private Long odontologoId;
    private LocalDate fecha;
}
