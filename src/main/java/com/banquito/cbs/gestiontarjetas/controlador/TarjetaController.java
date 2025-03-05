package com.banquito.cbs.gestiontarjetas.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.cbs.gestiontarjetas.dto.TarjetaDto;
import com.banquito.cbs.gestiontarjetas.dto.CrearTarjetaDto;
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

    @Operation(summary = "Emitir nueva tarjeta", description = "Crea una nueva tarjeta con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarjeta creada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de tarjeta inválidos")
    })
    @PostMapping
    public ResponseEntity<TarjetaDto> emitir(
            @Parameter(description = "Datos de la tarjeta a crear", required = true)
            @RequestBody CrearTarjetaDto datosTarjeta) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicio.crearTarjeta(datosTarjeta));
        } catch (EntidadNoEncontradaException e) {
            log.error("Error al emitir tarjeta para cuenta {}", datosTarjeta.getIdCuentaTarjeta(), e);
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

    @Operation(summary = "Obtener tarjeta por número", description = "Devuelve una tarjeta específica por su número.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada")
    })
    @GetMapping()
    public ResponseEntity<TarjetaDto> obtenerPorNumero(
            @Parameter(description = "Número de la tarjeta", required = true) 
            @RequestParam(name = "numero") String numero) {
        log.info("Buscando tarjeta con número: {}", numero);
        try {
            return ResponseEntity.ok(mapper.toDto(servicio.buscarPorNumero(numero)));
        } catch (EntidadNoEncontradaException e) {
            log.error("Tarjeta con número {} no encontrada", numero, e);
            return ResponseEntity.notFound().build();
        }
    }
}
