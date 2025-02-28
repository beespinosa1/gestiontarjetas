package com.banquito.cbs.gestiontarjetas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CuentaTarjetaDto {
    
    @NotNull(message = "El ID del cliente es requerido")
    private Integer idCliente;

    @NotNull(message = "El cupo es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El cupo debe ser mayor a 0")
    private BigDecimal cupo;

}
