package com.ClinicaOdo.UP;

import com.ClinicaOdo.UP.entity.Paciente;
import com.ClinicaOdo.UP.service.TurnoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class TurnoTestService {

    @Autowired
    private TurnoService turnoService;

    private Paciente crearPacienteTU(String email){
        Paciente paciente = new Paciente();
        paciente.setNombre("Sergio");
        paciente.setApellido("Conti");
        paciente.setNumeroContacto(123456789);
        paciente.setFechaIngreso(LocalDate.now());
        paciente.setEmail(email);

        return paciente;
    }
}
