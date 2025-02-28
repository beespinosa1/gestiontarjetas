package com.banquito.cbs.gestiontarjetas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;
import java.util.Optional;

@Repository
public interface CuentaTarjetaRepository extends JpaRepository<CuentaTarjeta, Integer> {
    Optional<CuentaTarjeta> findTopByOrderByNumeroDesc();
}
