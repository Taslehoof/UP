package com.ClinicaOdo.UP;

import com.ClinicaOdo.UP.dto.TurnoDTO;
import com.ClinicaOdo.UP.entity.Odontologo;
import com.ClinicaOdo.UP.entity.Paciente;
import com.ClinicaOdo.UP.entity.Turno;
import com.ClinicaOdo.UP.service.TurnoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    private Odontologo crearOdontologoTU(String matricula){
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Andres");
        odontologo.setApellido("Serrano");
        odontologo.setMatricula(matricula);

        return odontologo;
    }


    private Turno crearTurnoTU(LocalDate fechaTurno){
       Turno turno = new Turno();
       turno.setPaciente(crearPacienteTU("Sergio@up.edu"));
       turno.setOdontologo(crearOdontologoTU("FakeTU1234"));
       turno.setFecha(fechaTurno);

       return turno;
    }

    @Test
    void guardarTurno_DeberiaPersistirYRetornoar() {
        // DADO
        System.out.println("Probando guardar un Turno");
        Turno turno = crearTurnoTU(LocalDate.of(2025,12,25));

        // CUANDO
        TurnoDTO turnoAgendado = turnoService.guardarTurno(turno);

        // ENTONCES
        assertNotNull(turnoAgendado.getId(),"Debe retornar un ID luego de guardar");
        Optional<TurnoDTO> buscado = turnoService.buscarPorId(turnoAgendado.getId());
        assertTrue(buscado.isPresent(),"El odontologo debe existir");
        assertEquals(turnoAgendado.getId(),buscado.get().getId(),"El id del Turno es incorrecto");
        assertEquals(turnoAgendado.getPacienteNombre(),buscado.get().getPacienteNombre(),"El nombre del Paciente del Turno guardado es Incorrecto");
        assertEquals("Sergio", buscado.get().getPacienteNombre(),"El nombre del Paciente del Turno guardado es Incorrecto");
        assertEquals(turnoAgendado.getOdontologoNombre(), buscado.get().getOdontologoNombre(),"El  nombre del Odontologo del Turno guardado es Incorrecto");
        assertEquals("Andres", buscado.get().getOdontologoNombre(), "El nombre del Odontologo del Turno guardado es Incorrecto");
    }

    @Test
    void borrarTurno_DeberiaEliminarTurno(){
        // DADO
        System.out.println("Probando eliminar un Turno");
        Turno turno = crearTurnoTU(LocalDate.of(2025,12,25));
        TurnoDTO turnoAgendado = turnoService.guardarTurno(turno);
        Long idTurno = turnoAgendado.getId();
        assertNotNull(idTurno, "Debe retornar un ID luego de agendar Turno");

        // CUANDO
        turnoService.borrarTurno(idTurno);

        // ENTONCES
        Optional<TurnoDTO> eliminado = turnoService.buscarPorId(idTurno);
        assertTrue(eliminado.isEmpty(),"El turno debe haber sido eliminado");
    }

}
