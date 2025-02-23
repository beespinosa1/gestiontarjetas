package com.banquito.cbs.gestiontarjetas.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.banquito.cbs.gestiontarjetas.dto.ErrorDto;

@RestControllerAdvice
public class ManejadorExcepciones {
    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<ErrorDto> handleEntidadNoEncontradaException(EntidadNoEncontradaException ex) {
        ErrorDto error = new ErrorDto();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EntidadDuplicadaException.class)
    public ResponseEntity<ErrorDto> handleEntidadDuplicadaException(EntidadDuplicadaException ex) {
        ErrorDto error = new ErrorDto();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(OperacionInvalidaException.class)
    public ResponseEntity<ErrorDto> handleOperacionInvalidaException(OperacionInvalidaException ex) {
        ErrorDto error = new ErrorDto();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
