package com.banquito.cbs.gestiontarjetas.excepcion;

public class EntidadNoEncontradaExcepcion extends RuntimeException {
    public EntidadNoEncontradaExcepcion(String message) {
        super(message);
    }
}
