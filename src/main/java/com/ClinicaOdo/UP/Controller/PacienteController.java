package com.ClinicaOdo.UP.Controller;

import com.ClinicaOdo.UP.Exception.BadEmailException;
import com.ClinicaOdo.UP.Exception.PacienteYaRegistradoException;
import com.ClinicaOdo.UP.Exception.ResourceNotFoundException;
import com.ClinicaOdo.UP.entity.Paciente;
import com.ClinicaOdo.UP.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService){
        this.pacienteService = pacienteService;
    }

    @GetMapping("/BuscarPorId/{id}")
    public ResponseEntity<Optional<Paciente>> buscarPorId(@PathVariable Long id){
        Optional<Paciente>  pacienteBuscado = pacienteService.buscarPorId(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(){
        return ResponseEntity.ok(pacienteService.buscarTodosLosPacientes());
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) throws BadEmailException, PacienteYaRegistradoException {

        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(paciente.getEmail());
        if(pacienteBuscado.isPresent()){
            throw new PacienteYaRegistradoException("El paciente a registrar ya existe con ese mail");
        } else {
            if(paciente.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
            } else {
                throw new BadEmailException("Email incorrectamente ingresado");
            }
        }
        /*if(pacienteBuscado.isPresent()){
            throw new PacienteYaRegistradoException("El paciente a registrar ya existe con ese mail");
        } else {
            if(paciente.getEmail().matches("^[A-Za-z0-9+_.-]+@fox.com")){
                return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
            } else {
                throw new BadEmailException("Email incorrectamente ingresado");
            }
        }*/
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(paciente.getId());
        if(pacienteBuscado.isEmpty()){
            throw new ResourceNotFoundException("Paciente no encontrado");
        } else {
            return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<Paciente>> buscarPorEmail(@PathVariable String email) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.borrarPaciente(id);
            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

}
