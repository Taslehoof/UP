package com.ClinicaOdo.UP.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    public Odontologo() {}

    public Odontologo( String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Odontologo{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", matricula='" + matricula + '\'' +
                ". id=" + id +
                '}';
    }
}
