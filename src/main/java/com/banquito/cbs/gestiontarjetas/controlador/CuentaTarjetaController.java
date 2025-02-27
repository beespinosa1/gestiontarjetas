package com.banquito.cbs.gestiontarjetas.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;
import com.banquito.cbs.gestiontarjetas.servicio.CuentaTarjetaService;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.CuentaTarjetaMapper;
import com.banquito.cbs.gestiontarjetas.excepcion.EntidadNoEncontradaException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/v1/cuentas-tarjetas")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class CuentaTarjetaController {
    
    private final CuentaTarjetaService servicio;
    private final CuentaTarjetaMapper mapper;

    @Operation(summary = "Listar todas las cuentas tarjeta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuentas listadas exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<CuentaTarjetaDto>> listarTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @Operation(summary = "Obtener cuenta tarjeta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CuentaTarjetaDto> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(mapper.toDto(servicio.buscarPorId(id)));
        } catch (EntidadNoEncontradaException e) {
            log.error("Cuenta tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear nueva cuenta tarjeta")
    @PostMapping
    public ResponseEntity<CuentaTarjetaDto> crear(@RequestBody CuentaTarjetaDto cuentaTarjetaDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(servicio.crear(cuentaTarjetaDto));
    }

    @Operation(summary = "Activar cuenta tarjeta")
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Integer id) {
        try {
            servicio.activarCuenta(id);
            return ResponseEntity.ok().build();
        } catch (EntidadNoEncontradaException e) {
            log.error("Cuenta tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Inactivar cuenta tarjeta")
    @PutMapping("/{id}/inactivar")
    public ResponseEntity<Void> inactivar(@PathVariable Integer id) {
        try {
            servicio.inactivarCuenta(id);
            return ResponseEntity.ok().build();
        } catch (EntidadNoEncontradaException e) {
            log.error("Cuenta tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
