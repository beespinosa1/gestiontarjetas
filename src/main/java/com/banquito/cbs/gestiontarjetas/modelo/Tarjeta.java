package com.banquito.cbs.gestiontarjetas.modelo;

import java.time.LocalDate;

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
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NUMERO", length = 16, nullable = false)
    private String numero;

    @Column(name = "FECHA_EMISION", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "ESTADO", length = 3, nullable = false)
    private String estado;
}
