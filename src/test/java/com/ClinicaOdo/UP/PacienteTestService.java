package com.ClinicaOdo.UP;

import com.ClinicaOdo.UP.entity.Paciente;
import com.ClinicaOdo.UP.service.PacienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PacienteTestService {
    @Autowired
    private PacienteService pacienteService;

    private Paciente crearPacienteTestUnitario(String email){

       Paciente paciente = new Paciente();

       paciente.setNombre("Sergio");
       paciente.setApellido("Conti");
       paciente.setNumeroContacto(123456789);
       paciente.setFechaIngreso(LocalDate.now());
       paciente.setEmail(email);

       return paciente;
    }

    @Test
    void guardarPaciente_PersisteYRetornar(){
        // DADO
        System.out.println("Probando guardar un paciente");
        Paciente paciente = crearPacienteTestUnitario("sergio.conti@up.edu");

        // CUANDO
        Paciente guardado = pacienteService.guardarPaciente(paciente);

        // ENTONCES
        assertNotNull(guardado.getId(), "El id del paciente no debería ser nulo luego de guardar");
        Optional<Paciente> buscado = pacienteService.buscarPorId(guardado.getId());
        assertTrue(buscado.isPresent(), "El paciente debería existeir en la base de datos");
        assertEquals(guardado.getId(), buscado.get().getId(),"El id del paciente guardado es incorrecto");
        assertEquals(guardado.getId(), buscado.get().getNombre(),"El nombre del paciente guardado es incorrecto");
    }

    @Test
    void buscarPorEmail_deberiaEncontrarPaciente(){
        // DADO
        System.out.println("Iniciando prueba busqueda");
        String email = "maria.garcia@up.edu";
        Paciente paciente = crearPacienteTestUnitario(email);
        pacienteService.guardarPaciente(paciente);

        // CUANDO
        Optional<Paciente> resultado = pacienteService.buscarPorEmail(email);

        // ENTONCES
        assertTrue(resultado.isPresent(),"Debería encontar un paciente con ese email");
        assertEquals(email, resultado.get().getEmail(), "El email del paciente encontrado no coincide");
    }

    @Test
    void borrarPaciente_deberiaEliminarPaciente(){
       // DADO
        System.out.println("Prueba eliminacion de Paciente");
        Paciente paciente = crearPacienteTestUnitario("borrar@up.edu");
        Paciente guardado = pacienteService.guardarPaciente(paciente);
        Long id = guardado.getId();
        assertNotNull(id, "El id del paciente de prueba no deberia ser nulo");

       // CUANDO
        pacienteService.borrarPaciente(id);

       // ENTONCES
        Optional<Paciente> eliminado = pacienteService.buscarPorId(id);
        assertTrue(eliminado.isEmpty(), "El paciente deberia ser eliminado");
    }

    @Test
    void modificarPaciente_deberiaModificarPaciente() {
        // DADO
        System.out.println("Prueba modificacion Paciente");
        Paciente paciente = crearPacienteTestUnitario("modificar@up.edu");
        Paciente guardado = pacienteService.guardarPaciente(paciente);
        Long id = guardado.getId();
        assertNotNull(id, "El id del paciente de prueba no deberia ser nulo");

        // CUANDO
        guardado.setNombre("Juan");
        pacienteService.guardarPaciente(guardado);

        // ENTONCES
        Optional<Paciente> actualizado = pacienteService.buscarPorId(id);
        assertEquals("Juan",actualizado.get().getNombre(), "El nombre del paciente no coincide luego de modificarlo");
    }
}
