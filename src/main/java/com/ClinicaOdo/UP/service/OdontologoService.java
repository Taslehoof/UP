package com.ClinicaOdo.UP.service;

import com.ClinicaOdo.UP.dto.OdontologoDTO;
import com.ClinicaOdo.UP.entity.Odontologo;
import com.ClinicaOdo.UP.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarPorId(Long id){
        return odontologoRepository.findById(id);
    }

    public Optional<Odontologo> buscarPorNombre(String nombre){
        return odontologoRepository.findByNombre(nombre);
    }

    public List<Odontologo> buscarTodosLosOdontologos(){
        return odontologoRepository.findAll();
    }

    public List<OdontologoDTO> buscarTodosLosOdontologosDTO(){
        return odontologoRepository.findAll().stream().map(this::odontologoAOdontologoDTO).collect(Collectors.toList());
    }

    public Optional<Odontologo> buscarPorMatricula(String matricula){
        return odontologoRepository.findByMatricula(matricula);
    }

    public void borrarOdontologo(Long id){
        odontologoRepository.deleteById(id);
    }

    public OdontologoDTO odontologoAOdontologoDTO(Odontologo odontologo){

        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(odontologo.getId());
        odontologoDTO.setNombre(odontologo.getNombre());
        odontologoDTO.setApellido(odontologo.getApellido());

        return odontologoDTO;
    }
}
