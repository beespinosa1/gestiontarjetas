package com.banquito.cbs.gestiontarjetas.servicio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import com.banquito.cbs.gestiontarjetas.dto.TarjetaDto;
import com.banquito.cbs.gestiontarjetas.dto.CrearTarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.Tarjeta;
import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;
import com.banquito.cbs.gestiontarjetas.repositorio.TarjetaRepository;
import com.banquito.cbs.gestiontarjetas.repositorio.CuentaTarjetaRepository;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.TarjetaPeticionMapper;
import com.banquito.cbs.gestiontarjetas.excepcion.EntidadNoEncontradaException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarjetaService {
    
    private final TarjetaRepository repositorio;
    private final CuentaTarjetaRepository cuentaTarjetaRepository;
    private final TarjetaPeticionMapper mapper;

    public static final String ENTITY_NAME = "Tarjeta";
    public static final String ESTADO_ACTIVA = "ACT";
    public static final String ESTADO_INACTIVA = "INA";
    public static final String ESTADO_BLOQUEADA = "BLO";

    public List<Tarjeta> listarPorCuentaTarjeta(Integer idCuentaTarjeta) {
        List<Tarjeta> tarjetas = repositorio.findByCuentaTarjetaId(idCuentaTarjeta);
        if (tarjetas.isEmpty()) {
            throw new EntidadNoEncontradaException(
                String.format("No se encontraron tarjetas para la cuenta: %d", idCuentaTarjeta));
        }
        return tarjetas;
    }

    public Tarjeta buscarPorId(Integer id) {
        return repositorio.findById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException(
                String.format("No se encontró la tarjeta con ID: %d", id)));
    }

    public Tarjeta buscarPorNumero(String numeroTarjeta) {
        return repositorio.findByNumero(numeroTarjeta)
            .orElseThrow(() -> new EntidadNoEncontradaException(
                String.format("No se encontró la tarjeta con número: %s", numeroTarjeta)));
    }

    @Transactional
    public TarjetaDto crearTarjeta(CrearTarjetaDto datosTarjeta) {
        CuentaTarjeta cuentaTarjeta = cuentaTarjetaRepository.findById(datosTarjeta.getIdCuentaTarjeta())
            .orElseThrow(() -> new EntidadNoEncontradaException(
                String.format("No se encontró la cuenta tarjeta con ID: %d", datosTarjeta.getIdCuentaTarjeta())));

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setCuentaTarjeta(cuentaTarjeta);
        tarjeta.setNumero(datosTarjeta.getNumero());
        tarjeta.setFechaEmision(LocalDateTime.now().toLocalDate());
        tarjeta.setEstado(datosTarjeta.getEstado());

        log.info("Creando tarjeta para cuenta: {}", datosTarjeta.getIdCuentaTarjeta());
        return mapper.toDto(repositorio.save(tarjeta));
    }

    @Transactional
    public void activarTarjeta(Integer id) {
        Tarjeta tarjeta = buscarPorId(id);
        tarjeta.setEstado(ESTADO_ACTIVA);
        repositorio.save(tarjeta);
        log.info("Tarjeta {} activada", id);
    }

    @Transactional
    public void inactivarTarjeta(Integer id) {
        Tarjeta tarjeta = buscarPorId(id);
        tarjeta.setEstado(ESTADO_INACTIVA);
        repositorio.save(tarjeta);
        log.info("Tarjeta {} inactivada", id);
    }

    @Transactional
    public void bloquearTarjeta(Integer id) {
        Tarjeta tarjeta = buscarPorId(id);
        tarjeta.setEstado(ESTADO_BLOQUEADA);
        repositorio.save(tarjeta);
        log.info("Tarjeta {} bloqueada", id);
    }
}
