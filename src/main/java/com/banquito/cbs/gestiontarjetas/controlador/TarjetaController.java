package com.banquito.cbs.gestiontarjetas.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.cbs.gestiontarjetas.dto.TarjetaDto;
import com.banquito.cbs.gestiontarjetas.servicio.TarjetaService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/v1/tarjetas")
@RequiredArgsConstructor
public class TarjetaController {
    
    private final TarjetaService tarjetaService;
    
    @PostMapping
    public ResponseEntity<TarjetaDto> crear(@RequestBody TarjetaDto tarjetaDto) {
        return ResponseEntity.ok(tarjetaService.crear(tarjetaDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarjetaDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tarjetaService.obtenerPorId(id));
    }

    @GetMapping("/cuenta/{idCuentaTarjeta}")
    public ResponseEntity<List<TarjetaDto>> obtenerPorCuentaTarjeta(@PathVariable Integer idCuentaTarjeta) {
        return ResponseEntity.ok(tarjetaService.obtenerPorCuentaTarjeta(idCuentaTarjeta));
    }

    @GetMapping
    public ResponseEntity<List<TarjetaDto>> obtenerTodos() {
        return ResponseEntity.ok(tarjetaService.obtenerTodos());
    }
}
