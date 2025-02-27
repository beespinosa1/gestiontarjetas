package com.banquito.cbs.gestiontarjetas.servicio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;
import com.banquito.cbs.gestiontarjetas.repositorio.CuentaTarjetaRepository;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.CuentaTarjetaMapper;
import com.banquito.cbs.gestiontarjetas.excepcion.EntidadNoEncontradaException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaTarjetaService {
    
    private final CuentaTarjetaRepository repositorio;
    private final CuentaTarjetaMapper mapper;

    public static final String ENTITY_NAME = "CuentaTarjeta";
    public static final String ESTADO_ACTIVA = "ACT";
    public static final String ESTADO_INACTIVA = "INA";

    @Transactional
    public CuentaTarjetaDto crear(CuentaTarjetaDto cuentaTarjetaDto) {
        CuentaTarjeta cuentaTarjeta = mapper.toEntity(cuentaTarjetaDto);
        cuentaTarjeta.setEstado(ESTADO_ACTIVA);
        log.info("Creando cuenta tarjeta");
        return mapper.toDto(repositorio.save(cuentaTarjeta));
    }

    public CuentaTarjeta buscarPorId(Integer id) {
        return repositorio.findById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException(
                String.format("No se encontró la cuenta tarjeta con ID: %d", id)));
    }

    @Transactional
    public void activarCuenta(Integer id) {
        CuentaTarjeta cuenta = buscarPorId(id);
        cuenta.setEstado(ESTADO_ACTIVA);
        repositorio.save(cuenta);
        log.info("Cuenta tarjeta {} activada", id);
    }

    @Transactional
    public void inactivarCuenta(Integer id) {
        CuentaTarjeta cuenta = buscarPorId(id);
        cuenta.setEstado(ESTADO_INACTIVA);
        repositorio.save(cuenta);
        log.info("Cuenta tarjeta {} inactivada", id);
    }

    public CuentaTarjetaDto obtenerPorId(Integer id) {
        return repositorio.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new RuntimeException("Cuenta Tarjeta no encontrada"));
    }

    public List<CuentaTarjetaDto> obtenerTodos() {
        return repositorio.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
}
