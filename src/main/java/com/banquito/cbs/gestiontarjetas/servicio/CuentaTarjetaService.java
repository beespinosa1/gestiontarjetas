package com.banquito.cbs.gestiontarjetas.servicio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;
import com.banquito.cbs.gestiontarjetas.repositorio.CuentaTarjetaRepository;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.CuentaTarjetaMapper;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaTarjetaService {
    
    private final CuentaTarjetaRepository cuentaTarjetaRepository;
    private final CuentaTarjetaMapper cuentaTarjetaMapper;

    @Transactional
    public CuentaTarjetaDto crear(CuentaTarjetaDto cuentaTarjetaDto) {
        CuentaTarjeta cuentaTarjeta = cuentaTarjetaMapper.toEntity(cuentaTarjetaDto);
        return cuentaTarjetaMapper.toDto(cuentaTarjetaRepository.save(cuentaTarjeta));
    }

    public CuentaTarjetaDto obtenerPorId(Integer id) {
        return cuentaTarjetaRepository.findById(id)
            .map(cuentaTarjetaMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Cuenta Tarjeta no encontrada"));
    }

    public List<CuentaTarjetaDto> obtenerTodos() {
        return cuentaTarjetaRepository.findAll().stream()
            .map(cuentaTarjetaMapper::toDto)
            .collect(Collectors.toList());
    }
}
