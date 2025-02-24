package com.banquito.cbs.gestiontarjetas.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;
import com.banquito.cbs.gestiontarjetas.servicio.CuentaTarjetaService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/v1/cuentas-tarjetas")
@RequiredArgsConstructor
public class CuentaTarjetaController {
    
    private final CuentaTarjetaService cuentaTarjetaService;
    
    @PostMapping
    public ResponseEntity<CuentaTarjetaDto> crear(@RequestBody CuentaTarjetaDto cuentaTarjetaDto) {
        return ResponseEntity.ok(cuentaTarjetaService.crear(cuentaTarjetaDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaTarjetaDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(cuentaTarjetaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CuentaTarjetaDto>> obtenerTodos() {
        return ResponseEntity.ok(cuentaTarjetaService.obtenerTodos());
    }
}
