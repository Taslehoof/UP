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
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
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
        Paciente paciente = new Paciente("Homero","Simpson",123123, LocalDate.of(2025,11,6),domicilio,"homero@fox.com");
        pacienteRepository.save(paciente);

        Domicilio domicilio2 = new Domicilio("Siempre Viva",1212,"Springfield","Mississipi");
        Paciente paciente2 = new Paciente("Marge","Simpson",123123, LocalDate.of(2025,11,6),domicilio2,"marge@fox.com");
        pacienteRepository.save(paciente2);

        Domicilio domicilioNed = new Domicilio("Siempre Viva",1212,"Springfield","Mississipi");
        Paciente paciente3 = new Paciente("Ned","Flanders",123123, LocalDate.of(2025,11,6),domicilioNed,"ned@encristo.com");
        pacienteRepository.save(paciente3);

        Domicilio domicilioCarl = new Domicilio("Avenida Siempre Viva",742,"Springfield","Mississipi");
        Paciente pacienteCarl = new Paciente("Carl","Carlson",3335456, LocalDate.of(2025,11,23),domicilioCarl,"carl@planta.com");
        pacienteRepository.save(pacienteCarl);

        Domicilio domicilioLenny = new Domicilio("Avenida Siempre Viva",744,"Springfield","Mississipi");
        Paciente pacienteLenny = new Paciente("Lenny","Leonard",3345676, LocalDate.of(2025,2,6),domicilioLenny,"lenny@planta.com");
        pacienteRepository.save(pacienteLenny);

        Domicilio domicilioBurns = new Domicilio("Mansion Burns",1,"Springfield","Mississipi");
        Paciente pacienteBurns = new Paciente("Montgomery","Burns",877788996, LocalDate.of(2025,1,27),domicilioBurns,"Burns@planta.com");
        pacienteRepository.save(pacienteBurns);

        Domicilio domicilioSmithers = new Domicilio("Mansion Burns",2,"Springfield","Mississipi");
        Paciente pacienteSmithers = new Paciente("Waylon","Smithers",1432623, LocalDate.of(2025,8,16),domicilioSmithers,"smithers@planta.com");
        pacienteRepository.save(pacienteSmithers);

        Odontologo odontolgo = new Odontologo("Nick","Rivera","Fake123123");
        odontologoRepository.save(odontolgo);

        Odontologo odontolgoHibbert = new Odontologo("Julius","Hibbert","SPR1001");
        odontologoRepository.save(odontolgoHibbert);

        Odontologo odontolgoMonroe = new Odontologo("Marvin","Monroe","SPR1002");
        odontologoRepository.save(odontolgoMonroe);

        Turno turno = new Turno(paciente,odontolgo,LocalDate.of(2025,11,16));
        turnoRepository.save(turno);

        Turno turno2 = new Turno(paciente3,odontolgo,LocalDate.of(2025,11,15));
        turnoRepository.save(turno2);

        Turno turno3 = new Turno(paciente3,odontolgo,LocalDate.of(2025,9,16));
        turnoRepository.save(turno3);
    }
}
