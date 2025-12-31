package com.ClinicaOdo.UP;

import com.ClinicaOdo.UP.entity.Odontologo;
import com.ClinicaOdo.UP.service.OdontologoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OdontologoTestService {
    @Autowired
    private OdontologoService odontologoService;

    private Odontologo crearOdontologoTestUnitario(){

       Odontologo odontologo = new Odontologo();

       odontologo.setNombre("Andres");
       odontologo.setApellido("Serrano");
       odontologo.setMatricula("FakeTu1234");

       return odontologo;
    }

   @Test
   void guardarOdontologo_DeberiaPersisteYRetornar(){
        // DADO
       System.out.println("Probando guardar un Odontologo");
       Odontologo odontologo = crearOdontologoTestUnitario();

       // CUANDO
       Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

       // ENTONCES
       assertNotNull(odontologoGuardado.getId(),"Debe retornar un ID luego de guardar");
       Optional<Odontologo> buscado = odontologoService.buscarPorId(odontologoGuardado.getId());
       assertTrue(buscado.isPresent(),"El odontologo debe existir");
       assertEquals(odontologoGuardado.getId(),buscado.get().getId(), "El id del Odontolgo es Incorrecto");
       assertEquals(odontologoGuardado.getNombre(), buscado.get().getNombre(),"El nombre del odontolgoo guardado es incorrecto");
   }

   @Test
   void borrarOdontologo_DeberiaEliminarOdontologo(){
       // DADO
       System.out.println("Iniciando prueba eliminar un Odontologo");
       Odontologo odontologo = crearOdontologoTestUnitario();
       Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
       Long idOdontologo = odontologoGuardado.getId();
       assertNotNull(odontologoGuardado.getId(), "Debe retornar un ID luago de guardar");

       // CUANDO
       odontologoService.borrarOdontologo(idOdontologo);

       // ENTONCES
       Optional<Odontologo> eliminado = odontologoService.buscarPorId(idOdontologo);
       assertTrue(eliminado.isEmpty(), "El odontologo deberia haber sido eliminado");
   }

   @Test
    void modificarOdontologo_DeberiaModificarOdontologo(){
        // DADO
       System.out.println("Iniciando prueba modificar un Odontologo");
       Odontologo odontologo = crearOdontologoTestUnitario();
       Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
       Long idOdontologo = odontologoGuardado.getId();
       assertNotNull(odontologoGuardado.getId(),"Debe retornar un ID luego de guardar");

       // CUANDO
       odontologoGuardado.setNombre("Carlos");
       odontologoService.guardarOdontologo(odontologoGuardado);

       // ENTONCES
       Optional<Odontologo> actualizado = odontologoService.buscarPorId(idOdontologo);
       assertEquals("Carlos", actualizado.get().getNombre(),"El nombre del odontologo no coincide luego de modoficarlo");
   }
}
