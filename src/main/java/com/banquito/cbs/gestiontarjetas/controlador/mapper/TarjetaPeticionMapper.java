package com.banquito.cbs.gestiontarjetas.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.gestiontarjetas.dto.TarjetaDto;
import com.banquito.cbs.gestiontarjetas.modelo.Tarjeta;
import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TarjetaPeticionMapper {
    TarjetaDto toDto(Tarjeta tarjeta);
    Tarjeta toEntity(TarjetaDto tarjetaDto);

    static Tarjeta mapToTarjeta(TarjetaDto tarjetaDto, CuentaTarjeta cuentaTarjeta) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(tarjetaDto.getId());
        tarjeta.setCuentaTarjeta(cuentaTarjeta);
        tarjeta.setNumero(tarjetaDto.getNumero());
        tarjeta.setFechaEmision(tarjetaDto.getFechaEmision());
        tarjeta.setEstado(tarjetaDto.getEstado());
        return tarjeta;
    }

    static TarjetaDto mapToTarjetaDto(Tarjeta tarjeta) {
        TarjetaDto tarjetaDto = new TarjetaDto();
        tarjetaDto.setId(tarjeta.getId());
        tarjetaDto.setIdCuentaTarjeta(tarjeta.getCuentaTarjeta().getId());
        tarjetaDto.setNumero(tarjeta.getNumero());
        tarjetaDto.setFechaEmision(tarjeta.getFechaEmision());
        tarjetaDto.setEstado(tarjeta.getEstado());
        return tarjetaDto;
    }
}
