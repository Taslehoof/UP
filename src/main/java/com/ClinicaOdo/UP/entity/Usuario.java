package com.ClinicaOdo.UP.entity;

public class Usuario {

    private Long id;
    private String nombre;
    private String userName;
    private String password;
    private String email;
    private UsuarioRole usuarioRole;

    public Usuario(String nombre, String userName, String password, String email, UsuarioRole usuarioRole) {
        this.nombre = nombre;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.usuarioRole = usuarioRole;
    }
}
