package com.ClinicaOdo.UP.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Map<String, String>> tratamientoResourceNotFoundException(ResourceNotFoundException e){
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({BadEmailException.class})
    public ResponseEntity<Map<String, String>> tratamientoBadEmailException(BadEmailException e){
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({PacienteYaRegistradoException.class})
    public ResponseEntity<Map<String, String>> tratamientoBadEmailExceptioncienteYaRegistradoExcepotion(PacienteYaRegistradoException e){
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(body);
    }

}
