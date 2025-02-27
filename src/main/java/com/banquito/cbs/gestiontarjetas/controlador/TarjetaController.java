package com.banquito.cbs.gestiontarjetas.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.cbs.gestiontarjetas.dto.TarjetaDto;
import com.banquito.cbs.gestiontarjetas.servicio.TarjetaService;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.TarjetaPeticionMapper;
import com.banquito.cbs.gestiontarjetas.excepcion.EntidadNoEncontradaException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/tarjetas")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class TarjetaController {
    
    private final TarjetaService servicio;
    private final TarjetaPeticionMapper mapper;

    @Operation(summary = "Listar tarjetas por cuenta", description = "Devuelve todas las tarjetas asociadas a una cuenta específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjetas listadas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/cuenta/{idCuentaTarjeta}")
    public ResponseEntity<List<TarjetaDto>> listarPorCuenta(
            @Parameter(description = "ID de la cuenta tarjeta", required = true) 
            @PathVariable Integer idCuentaTarjeta) {
        try {
            List<TarjetaDto> tarjetas = servicio.listarPorCuentaTarjeta(idCuentaTarjeta).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
            return ResponseEntity.ok(tarjetas);
        } catch (EntidadNoEncontradaException e) {
            log.error("Cuenta tarjeta con ID {} no encontrada", idCuentaTarjeta, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener tarjeta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TarjetaDto> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(mapper.toDto(servicio.buscarPorId(id)));
        } catch (EntidadNoEncontradaException e) {
            log.error("Tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Emitir nueva tarjeta")
    @PostMapping("/cuenta/{idCuentaTarjeta}")
    public ResponseEntity<TarjetaDto> emitir(@PathVariable Integer idCuentaTarjeta) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicio.crearTarjeta(idCuentaTarjeta));
        } catch (EntidadNoEncontradaException e) {
            log.error("Error al emitir tarjeta para cuenta {}", idCuentaTarjeta, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Activar tarjeta")
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Integer id) {
        try {
            servicio.activarTarjeta(id);
            return ResponseEntity.ok().build();
        } catch (EntidadNoEncontradaException e) {
            log.error("Tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Inactivar tarjeta")
    @PutMapping("/{id}/inactivar")
    public ResponseEntity<Void> inactivar(@PathVariable Integer id) {
        try {
            servicio.inactivarTarjeta(id);
            return ResponseEntity.ok().build();
        } catch (EntidadNoEncontradaException e) {
            log.error("Tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Bloquear tarjeta")
    @PutMapping("/{id}/bloquear")
    public ResponseEntity<Void> bloquear(@PathVariable Integer id) {
        try {
            servicio.bloquearTarjeta(id);
            return ResponseEntity.ok().build();
        } catch (EntidadNoEncontradaException e) {
            log.error("Tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
