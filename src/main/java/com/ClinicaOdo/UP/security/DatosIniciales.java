package com.ClinicaOdo.UP.security;

import com.ClinicaOdo.UP.entity.*;
import com.ClinicaOdo.UP.repository.OdontologoRepository;
import com.ClinicaOdo.UP.repository.PacienteRepository;
import com.ClinicaOdo.UP.repository.TurnoRepository;
import com.ClinicaOdo.UP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

public class DatosIniciales  implements ApplicationRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private BCryptPasswordEncoder codificador;
    @Autowired
    private TurnoRepository turnoRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String pass = "admin";
        String passCifrado = codificador.encode(pass);
        Usuario usuario = new Usuario("Sergio","admin", passCifrado, "admin@admin.com", UsuarioRole.ROLE_ADMIN);
        System.out.println("Pass sin cifrar: " + pass + " Pass cifrado: " + passCifrado);
        usuarioRepository.save(usuario);

        Domicilio domicilio = new Domicilio("Siempre Viva",1212,"Springfield","Mississipi");
        Paciente paciente = new Paciente("Homero","Simpson",123123, LocalDate.of(2025,11,6),domicilio,"Homero@Fox.com");
        pacienteRepository.save(paciente);

        Odontologo odontolgo = new Odontologo("Nick","Rivera","Fake123123");
        odontologoRepository.save(odontolgo);

        Turno turno = new Turno(paciente,odontolgo,LocalDate.of(2025,11,16));
        turnoRepository.save(turno);

    }
}
