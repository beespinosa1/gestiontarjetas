package com.banquito.cbs.gestiontarjetas.excepcion;

public class EntidadNoEncontradaException extends RuntimeException {
    
    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
