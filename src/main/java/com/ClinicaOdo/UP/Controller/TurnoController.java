package com.ClinicaOdo.UP.Controller;

import com.ClinicaOdo.UP.Exception.ResourceNotFoundException;
import com.ClinicaOdo.UP.dto.AgendarTurnoDTO;
import com.ClinicaOdo.UP.dto.TurnoDTO;
import com.ClinicaOdo.UP.entity.Odontologo;
import com.ClinicaOdo.UP.entity.Paciente;
import com.ClinicaOdo.UP.entity.Turno;
import com.ClinicaOdo.UP.service.OdontologoService;
import com.ClinicaOdo.UP.service.PacienteService;
import com.ClinicaOdo.UP.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    private OdontologoService odontologoService;
    private PacienteService pacienteService;
    private TurnoService turnoService;

    @Autowired
    public TurnoController(OdontologoService odontologoService, PacienteService pacienteService, TurnoService turnoService){
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
    }
    
    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody AgendarTurnoDTO agendarTurnoDTO) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(agendarTurnoDTO.getPacienteId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(agendarTurnoDTO.getOdontologoId());

        //Validar turnos que sean de hoy en adelante
        /*if(pacienteBuscado.isPresent() && odontologoBuscado.isPresent()){
                Turno turno = new Turno(
                    pacienteBuscado.get(),
                    odontologoBuscado.get(),
                    agendarTurnoDTO.getFecha().isEqual(LocalDate.now())
                );*/

        if(pacienteBuscado.isPresent() && odontologoBuscado.isPresent()){
            Turno turno = new Turno(
                    pacienteBuscado.get(),
                    odontologoBuscado.get(),
                    agendarTurnoDTO.getFecha()
            );
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTurnoDTO(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping("/TurnoPorPaciente/{id}")
    public ResponseEntity<List<TurnoDTO>> listarTurnosDTOPorPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(id);
        if(pacienteBuscado.isEmpty()){
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        List<TurnoDTO> turnoBuscado = turnoService.buscarPorPaciente(id);
        return ResponseEntity.ok(turnoBuscado);
    }

    @GetMapping("/TurnoPorOdontologo/{id}")
    public ResponseEntity<List<TurnoDTO>> listarTurnosDTOPorOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        if(odontologoBuscado.isEmpty()){
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
        List<TurnoDTO> turnoBuscado = turnoService.buscarPorOdontologo(id);
        return ResponseEntity.ok(turnoBuscado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarPorId(id);
        if(turnoBuscado.isPresent()){
            turnoService.borrarTurno(id);
            return ResponseEntity.ok().build();
        } else {
          throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }
}
