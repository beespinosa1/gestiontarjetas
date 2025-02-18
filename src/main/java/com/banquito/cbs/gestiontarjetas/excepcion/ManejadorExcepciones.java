package com.banquito.cbs.gestiontarjetas.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorExcepciones {
    @ExceptionHandler(EntidadNoEncontradaExcepcion.class)
    public ResponseEntity<ErrorDto> handleEntidadNoEncontradaException(EntidadNoEncontradaExcepcion ex) {
        ErrorDto error = new ErrorDto();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EntidadDuplicadaExcepcion.class)
    public ResponseEntity<ErrorDto> handleEntidadDuplicadaException(EntidadDuplicadaExcepcion ex) {
        ErrorDto error = new ErrorDto();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(OperacionInvalidaExcepcion.class)
    public ResponseEntity<ErrorDto> handleOperacionInvalidaException(OperacionInvalidaExcepcion ex) {
        ErrorDto error = new ErrorDto();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
