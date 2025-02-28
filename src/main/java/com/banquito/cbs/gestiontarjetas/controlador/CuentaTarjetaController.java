package com.banquito.cbs.gestiontarjetas.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;
import com.banquito.cbs.gestiontarjetas.servicio.CuentaTarjetaService;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.CuentaTarjetaMapper;
import com.banquito.cbs.gestiontarjetas.excepcion.EntidadNoEncontradaException;
import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/cuentas-tarjetas")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class CuentaTarjetaController {
    
    private final CuentaTarjetaService servicio;
    private final CuentaTarjetaMapper mapper;

    @Operation(summary = "Listar todas las cuentas tarjeta", 
              description = "Devuelve todas las cuentas tarjeta registradas en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuentas listadas exitosamente", 
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<CuentaTarjetaDto>> listarTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @Operation(summary = "Obtener cuenta tarjeta por ID", 
              description = "Devuelve los detalles de una cuenta tarjeta específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta tarjeta encontrada exitosamente", 
                    content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Cuenta tarjeta no encontrada", 
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CuentaTarjetaDto> obtenerPorId(
            @Parameter(description = "ID de la cuenta tarjeta que se desea buscar", required = true) 
            @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(mapper.toDto(servicio.buscarPorId(id)));
        } catch (EntidadNoEncontradaException e) {
            log.error("Cuenta tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear nueva cuenta tarjeta", 
              description = "Permite registrar una nueva cuenta tarjeta en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cuenta tarjeta creada exitosamente", 
                    content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", 
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<CuentaTarjetaDto> crear(
            @Parameter(description = "Datos de la cuenta tarjeta a crear", required = true)
            @Valid @RequestBody CuentaTarjetaDto cuentaTarjetaDto) {
        CuentaTarjeta cuentaTarjeta = mapper.toEntity(cuentaTarjetaDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapper.toDto(servicio.crearCuentaTarjeta(cuentaTarjeta)));
    }

    @Operation(summary = "Activar cuenta tarjeta", 
              description = "Permite activar una cuenta tarjeta específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta tarjeta activada exitosamente", 
                    content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Cuenta tarjeta no encontrada", 
                    content = @Content)
    })
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activar(
            @Parameter(description = "ID de la cuenta tarjeta que se desea activar", required = true)
            @PathVariable Integer id) {
        try {
            servicio.activarCuenta(id);
            return ResponseEntity.ok().build();
        } catch (EntidadNoEncontradaException e) {
            log.error("Cuenta tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Inactivar cuenta tarjeta", 
              description = "Permite inactivar una cuenta tarjeta específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta tarjeta inactivada exitosamente", 
                    content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Cuenta tarjeta no encontrada", 
                    content = @Content)
    })
    @PutMapping("/{id}/inactivar")
    public ResponseEntity<Void> inactivar(
            @Parameter(description = "ID de la cuenta tarjeta que se desea inactivar", required = true)
            @PathVariable Integer id) {
        try {
            servicio.inactivarCuenta(id);
            return ResponseEntity.ok().build();
        } catch (EntidadNoEncontradaException e) {
            log.error("Cuenta tarjeta con ID {} no encontrada", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
