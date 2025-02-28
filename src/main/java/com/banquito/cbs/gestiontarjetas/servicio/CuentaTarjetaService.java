package com.banquito.cbs.gestiontarjetas.servicio;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;
import com.banquito.cbs.gestiontarjetas.repositorio.CuentaTarjetaRepository;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.CuentaTarjetaMapper;
import com.banquito.cbs.gestiontarjetas.excepcion.EntidadNoEncontradaException;
import com.banquito.cbs.gestiontarjetas.excepcion.OperacionInvalidaException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CuentaTarjetaService {
    
    private final CuentaTarjetaRepository repositorio;
    private final CuentaTarjetaMapper mapper;

    public static final String ENTITY_NAME = "CuentaTarjeta";
    public static final String ESTADO_ACTIVA = "ACT";
    public static final String ESTADO_INACTIVA = "INA";

    private static final String PREFIJO_CUENTA_TARJETA = "92"; 
    private static final BigInteger MAX_SECUENCIAL = new BigInteger("99999999999"); 

    public CuentaTarjetaService(CuentaTarjetaRepository repositorio, CuentaTarjetaMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    public List<CuentaTarjetaDto> obtenerTodos() {
        return repositorio.findAll().stream()
            .map(mapper::toDto)
            .toList();
    }

    public CuentaTarjeta buscarPorId(Integer id) {
        return repositorio.findById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException(
                String.format("No se encontró la cuenta tarjeta con ID: %d", id)));
    }

    public CuentaTarjeta crearCuentaTarjeta(CuentaTarjeta cuentaTarjeta) {
        if (cuentaTarjeta.getCupo() == null || cuentaTarjeta.getCupo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacionInvalidaException("El cupo debe ser mayor a cero");
        }
        
        if (cuentaTarjeta.getIdCliente() == null) {
            throw new OperacionInvalidaException("El ID del cliente es requerido");
        }
        
        cuentaTarjeta.setNumero(this.generarNuevoNumeroCuenta());
        cuentaTarjeta.setDisponible(cuentaTarjeta.getCupo());
        cuentaTarjeta.setUtilizado(BigDecimal.ZERO);
        cuentaTarjeta.setEstado(ESTADO_ACTIVA);
        
        log.info("Creando cuenta tarjeta con número: {}", cuentaTarjeta.getNumero());
        return repositorio.save(cuentaTarjeta);
    }

    public void activarCuenta(Integer id) {
        CuentaTarjeta cuenta = buscarPorId(id);
        cuenta.setEstado(ESTADO_ACTIVA);
        repositorio.save(cuenta);
        log.info("Cuenta tarjeta {} activada", id);
    }

    public void inactivarCuenta(Integer id) {
        CuentaTarjeta cuenta = buscarPorId(id);
        cuenta.setEstado(ESTADO_INACTIVA);
        repositorio.save(cuenta);
        log.info("Cuenta tarjeta {} inactivada", id);
    }

    private String generarNuevoNumeroCuenta() {
        CuentaTarjeta ultimaCuenta = repositorio.findTopByOrderByNumeroDesc()
            .orElse(null);
        
        BigInteger secuencial;
        if (ultimaCuenta == null) {
            secuencial = BigInteger.ONE;
        } else {
            String numeroSinPrefijo = ultimaCuenta.getNumero().substring(PREFIJO_CUENTA_TARJETA.length());
            secuencial = new BigInteger(numeroSinPrefijo).add(BigInteger.ONE);
        }

        if (secuencial.compareTo(MAX_SECUENCIAL) > 0) {
            throw new RuntimeException("No se pueden generar más números de cuenta tarjeta");
        }

        return String.format("%s%06d", PREFIJO_CUENTA_TARJETA, secuencial);
    }
}
