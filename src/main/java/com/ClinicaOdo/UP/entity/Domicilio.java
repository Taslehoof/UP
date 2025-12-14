package com.ClinicaOdo.UP.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.aot.generate.Generated;

@Getter
@Setter
public class Domicilio {
    private Long id;
    private String calle;
    private int numero;
    private String localidad;
    private String provincia;

    public Domicilio() {}

    public Domicilio(Long id, String calle, int numero, String localidad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Domicilio(String calle, int numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }
}
