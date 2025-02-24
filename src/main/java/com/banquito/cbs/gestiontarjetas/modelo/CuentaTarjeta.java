package com.banquito.cbs.gestiontarjetas.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "cuenta_tarjeta")
public class CuentaTarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ID_CLIENTE", nullable = false)
    private Integer idCliente;

    @Column(name = "NUMERO", length = 13, nullable = false)
    private String numero;

    @Column(name = "CUPO", precision = 20, scale = 2, nullable = false)
    private BigDecimal cupo;

    @Column(name = "UTILIZADO", precision = 20, scale = 2, nullable = false)
    private BigDecimal utilizado;

    @Column(name = "DISPONIBLE", precision = 20, scale = 2, nullable = false)
    private BigDecimal disponible;

    @Column(name = "ESTADO", length = 1, nullable = false)
    private String estado;
    
    
}
