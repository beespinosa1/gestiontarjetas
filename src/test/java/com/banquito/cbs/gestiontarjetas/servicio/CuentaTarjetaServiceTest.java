package com.banquito.cbs.gestiontarjetas.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banquito.cbs.gestiontarjetas.modelo.CuentaTarjeta;
import com.banquito.cbs.gestiontarjetas.repositorio.CuentaTarjetaRepository;
import com.banquito.cbs.gestiontarjetas.excepcion.EntidadNoEncontradaException;
import com.banquito.cbs.gestiontarjetas.excepcion.OperacionInvalidaException;
import com.banquito.cbs.gestiontarjetas.controlador.mapper.CuentaTarjetaMapper;
import com.banquito.cbs.gestiontarjetas.dto.CuentaTarjetaDto;

@ExtendWith(MockitoExtension.class)
public class CuentaTarjetaServiceTest {

    @Mock
    private CuentaTarjetaRepository cuentaTarjetaRepository;

    @Mock
    private CuentaTarjetaMapper mapper;

    @InjectMocks
    private CuentaTarjetaService cuentaTarjetaService;

    private CuentaTarjeta cuentaTarjeta;

    @BeforeEach
    void setUp() {
        cuentaTarjeta = new CuentaTarjeta();
        cuentaTarjeta.setId(1);
        cuentaTarjeta.setIdCliente(100);
        cuentaTarjeta.setNumero("92000001");
        cuentaTarjeta.setCupo(new BigDecimal("5000.00"));
        cuentaTarjeta.setDisponible(new BigDecimal("5000.00"));
        cuentaTarjeta.setUtilizado(BigDecimal.ZERO);
        cuentaTarjeta.setEstado("ACT");
    }

    @Test
    void whenCrearCuentaTarjeta_thenReturnCuentaTarjetaCreada() {
        // Arrange
        when(cuentaTarjetaRepository.save(any(CuentaTarjeta.class))).thenReturn(cuentaTarjeta);
        when(cuentaTarjetaRepository.findTopByOrderByNumeroDesc()).thenReturn(Optional.empty());

        // Act
        CuentaTarjeta cuentaCreada = cuentaTarjetaService.crearCuentaTarjeta(cuentaTarjeta);

        // Assert
        assertNotNull(cuentaCreada);
        assertEquals("92000001", cuentaCreada.getNumero());
        assertEquals(new BigDecimal("5000.00"), cuentaCreada.getCupo());
        assertEquals("ACT", cuentaCreada.getEstado());
        verify(cuentaTarjetaRepository).save(any(CuentaTarjeta.class));
    }

    @Test
    void whenCrearCuentaTarjetaSinCupo_thenThrowException() {
        // Arrange
        cuentaTarjeta.setCupo(BigDecimal.ZERO);

        // Act & Assert
        assertThrows(OperacionInvalidaException.class, () -> {
            cuentaTarjetaService.crearCuentaTarjeta(cuentaTarjeta);
        });
    }

    @Test
    void whenBuscarCuentaTarjetaPorId_thenReturnCuentaTarjeta() {
        // Arrange
        when(cuentaTarjetaRepository.findById(1)).thenReturn(Optional.of(cuentaTarjeta));

        // Act
        CuentaTarjeta cuentaEncontrada = cuentaTarjetaService.buscarPorId(1);

        // Assert
        assertNotNull(cuentaEncontrada);
        assertEquals(1, cuentaEncontrada.getId());
        assertEquals("92000001", cuentaEncontrada.getNumero());
    }

    @Test
    void whenBuscarCuentaTarjetaInexistente_thenThrowException() {
        // Arrange
        when(cuentaTarjetaRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadNoEncontradaException.class, () -> {
            cuentaTarjetaService.buscarPorId(99);
        });
    }

    @Test
    void whenListarCuentasTarjeta_thenReturnLista() {
        // Arrange
        List<CuentaTarjeta> cuentas = Arrays.asList(cuentaTarjeta);
        CuentaTarjetaDto dto = new CuentaTarjetaDto();
        when(cuentaTarjetaRepository.findAll()).thenReturn(cuentas);
        when(mapper.toDto(any(CuentaTarjeta.class))).thenReturn(dto);

        // Act
        List<CuentaTarjetaDto> listaCuentas = cuentaTarjetaService.obtenerTodos();

        // Assert
        assertNotNull(listaCuentas);
        assertEquals(1, listaCuentas.size());
        verify(cuentaTarjetaRepository).findAll();
        verify(mapper).toDto(any(CuentaTarjeta.class));
    }

    @Test
    void whenInactivarCuentaTarjeta_thenEstadoInactivo() {
        // Arrange
        when(cuentaTarjetaRepository.findById(1)).thenReturn(Optional.of(cuentaTarjeta));
        when(cuentaTarjetaRepository.save(any(CuentaTarjeta.class))).thenReturn(cuentaTarjeta);

        // Act
        cuentaTarjetaService.inactivarCuenta(1);

        // Assert
        assertEquals("INA", cuentaTarjeta.getEstado());
        verify(cuentaTarjetaRepository).save(cuentaTarjeta);
    }
} 