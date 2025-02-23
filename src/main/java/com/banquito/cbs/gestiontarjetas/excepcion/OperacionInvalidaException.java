package com.banquito.cbs.gestiontarjetas.excepcion;

public class OperacionInvalidaException extends RuntimeException {
    public OperacionInvalidaException(String message) {
        super(message);
    }
}
