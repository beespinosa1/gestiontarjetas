package com.banquito.cbs.gestiontarjetas.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.gestiontarjetas.dto.TarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.Tarjeta;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TarjetaPeticionMapper {
    TarjetaDto toDto(Tarjeta tarjeta);
    Tarjeta toEntity(TarjetaDto tarjetaDto);
}
