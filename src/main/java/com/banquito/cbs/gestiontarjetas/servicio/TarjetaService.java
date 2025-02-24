package com.banquito.cbs.gestiontarjetas.servicio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.cbs.gestiontarjetas.dto.TarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.Tarjeta;
import com.banquito.cbs.gestiontarjetas.repositorio.TarjetaRepository;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.TarjetaPeticionMapper;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarjetaService {
    
    private final TarjetaRepository tarjetaRepository;
    private final TarjetaPeticionMapper tarjetaMapper;

    @Transactional
    public TarjetaDto crear(TarjetaDto tarjetaDto) {
        Tarjeta tarjeta = tarjetaMapper.toEntity(tarjetaDto);
        return tarjetaMapper.toDto(tarjetaRepository.save(tarjeta));
    }

    public TarjetaDto obtenerPorId(Integer id) {
        return tarjetaRepository.findById(id)
            .map(tarjetaMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
    }

    public List<TarjetaDto> obtenerPorCuentaTarjeta(Integer idCuentaTarjeta) {
        return tarjetaRepository.findByCuentaTarjetaId(idCuentaTarjeta).stream()
            .map(tarjetaMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<TarjetaDto> obtenerTodos() {
        return tarjetaRepository.findAll().stream()
            .map(tarjetaMapper::toDto)
            .collect(Collectors.toList());
    }
}
