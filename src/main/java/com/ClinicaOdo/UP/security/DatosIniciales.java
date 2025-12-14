package com.ClinicaOdo.UP.security;

import com.ClinicaOdo.UP.entity.Usuario;
import com.ClinicaOdo.UP.entity.UsuarioRole;
import com.ClinicaOdo.UP.repository.OdontologoRepository;
import com.ClinicaOdo.UP.repository.PacienteRepository;
import com.ClinicaOdo.UP.repository.TurnoRepository;
import com.ClinicaOdo.UP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {

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
        String pass="admin";
        String passCifrado = codificador.encode(pass);
        Usuario usuario = new Usuario("Sergio","admin",passCifrado,"admin@admin.com", UsuarioRole.ROLE_ADMIN);
        System.out.println("Pass sin cifrar "+pass+"Pass Cifrado: "+passCifrado);
        usuarioRepository.save(usuario);
    }
}
