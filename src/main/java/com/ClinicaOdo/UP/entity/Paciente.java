package com.ClinicaOdo.UP.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Paciente {

    private Long id;
    private String nombre;
    private String apellido;
    private int numeroContacto;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;
    private String email;

    public Paciente(Long id, String nombre, String apellido, int numeroContacto, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
        this.email = email;
    }

    public Paciente() {}

    public Paciente( String nombre, String apellido, int numeroContacto, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ". id=" + id +
                '}';
    }
}
