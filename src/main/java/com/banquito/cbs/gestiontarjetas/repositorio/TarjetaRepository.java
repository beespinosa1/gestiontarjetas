package com.banquito.cbs.gestiontarjetas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.cbs.gestiontarjetas.modelo.Tarjeta;
import java.util.List;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer> {
    List<Tarjeta> findByCuentaTarjetaId(Integer idCuentaTarjeta);
}
