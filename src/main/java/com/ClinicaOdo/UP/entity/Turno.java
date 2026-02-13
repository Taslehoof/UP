package com.ClinicaOdo.UP.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paciente_id",referencedColumnName = "id")
    @JsonIgnore
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "odontologo_id",referencedColumnName = "id")
    @JsonIgnore
    private Odontologo odontologo;
    @Column
    private LocalDate fecha;

    public Turno (Paciente paciente, Odontologo odontologo, LocalDate fecha) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
    }


    public Turno(Paciente paciente, Odontologo odontologo, boolean equal) {}
}
