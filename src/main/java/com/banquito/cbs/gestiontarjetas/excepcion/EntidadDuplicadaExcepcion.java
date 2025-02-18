package com.banquito.cbs.gestiontarjetas.excepcion;

public class EntidadDuplicadaExcepcion extends RuntimeException {
    public EntidadDuplicadaExcepcion(String message) {
        super(message);
    }
}
