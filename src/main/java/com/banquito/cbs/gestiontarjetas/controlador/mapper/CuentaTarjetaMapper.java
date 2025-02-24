package com.banquito.cbs.gestiontarjetas.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CuentaTarjetaMapper {
    CuentaTarjetaDto toDto(CuentaTarjeta cuentaTarjeta);
    CuentaTarjeta toEntity(CuentaTarjetaDto cuentaTarjetaDto);
} 