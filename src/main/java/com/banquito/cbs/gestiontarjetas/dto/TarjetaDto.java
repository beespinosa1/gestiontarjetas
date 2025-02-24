package com.banquito.cbs.gestiontarjetas.dto;

import lombok.Data;
import lombok.ToString;
import java.time.LocalDate;

@Data
@ToString
public class TarjetaDto {
    private Integer id;
    private Integer idCuentaTarjeta;
    private String numero;
    private LocalDate fechaEmision;
    private String estado;
}
