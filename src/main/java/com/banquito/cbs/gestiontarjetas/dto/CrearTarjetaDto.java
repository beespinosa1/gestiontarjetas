package com.banquito.cbs.gestiontarjetas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para la creación de una tarjeta")
public class CrearTarjetaDto {
    
    @Schema(description = "ID de la cuenta tarjeta", required = true)
    private Integer idCuentaTarjeta;
    
    @Schema(description = "Número de la tarjeta", required = true)
    private String numero;
    
    @Schema(description = "Estado inicial de la tarjeta", required = true)
    private String estado;
} 