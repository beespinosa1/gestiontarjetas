package com.banquito.cbs.gestiontarjetas.excepcion;

public class OperacionInvalidaExcepcion extends RuntimeException {
    public OperacionInvalidaException(String message) {
        super(message);
    }
}
