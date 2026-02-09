package com.devsu.ing.deiberv.ms.cuenta.service.impl;

import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoRequest;
import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoRpt;
import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoSaldoDto;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoCuentaEnum;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
import com.devsu.ing.deiberv.ms.cuenta.events.publisher.MovimientoCreadoPublisher;
import com.devsu.ing.deiberv.ms.cuenta.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuenta.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuenta.mapper.MovimientoMapper;
import com.devsu.ing.deiberv.ms.cuenta.repository.MovimientosRepository;
import com.devsu.ing.deiberv.ms.cuenta.service.interfaces.CuentasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientosServiceImplTest {

  @Mock
  private MovimientosRepository movimientosRepository;

  @Mock
  private CuentasService cuentasService;

  @Mock
  private MovimientoMapper movimientoMapper;

  @Mock
  private MovimientoCreadoPublisher movimientoCreadoPublisher;

  @InjectMocks
  private MovimientosServiceImpl movimientosService;

  @Test
  void registrar_exitoso_debeRetornarDtoYSaldosActualizados() {
    Cuenta cuenta = Cuenta.builder().cuentaId(1L).numeroCuenta("123").saldo(new BigDecimal("100.00")).build();
    MovimientoRequest req = MovimientoRequest.builder().numeroCuenta("123").valor(new BigDecimal("50.00")).build();

    when(cuentasService.findByNroCuenta("123")).thenReturn(cuenta);
    when(movimientosRepository.save(any(Movimiento.class))).thenAnswer(inv -> {
      Movimiento m = inv.getArgument(0);
      m.setMovimientoId(1L);
      return m;
    });
    when(movimientoMapper.toMovimientoSaldoDto(any(Movimiento.class))).thenReturn(MovimientoSaldoDto.builder().idMovimiento("1").cuenta("123").soldoDisponible(new BigDecimal("150.00")).build());

    MovimientoSaldoDto dto = movimientosService.registrar(req);

    assertNotNull(dto);
    assertEquals("123", dto.getCuenta());
    assertEquals(new BigDecimal("150.00"), dto.getSoldoDisponible());
    verify(movimientosRepository, times(1)).save(any(Movimiento.class));
    verify(cuentasService, times(1)).findByNroCuenta("123");
  }

  @Test
  void registrar_saldoInsuficiente_debeLanzarSimpleException() {
    Cuenta cuenta = Cuenta.builder().cuentaId(1L).numeroCuenta("123").saldo(new BigDecimal("10.00")).build();
    MovimientoRequest req = MovimientoRequest.builder().numeroCuenta("123").valor(new BigDecimal("-50.00")).build();

    when(cuentasService.findByNroCuenta("123")).thenReturn(cuenta);

    SimpleException ex = assertThrows(SimpleException.class, () -> movimientosService.registrar(req));
    assertEquals(EnumError.SALDO_INSUFICIENTE, ex.getErrorEnum());
  }

  @Test
  void reporte_exitoso_debeRetornarLista() {
    // Preparar movimientos
    Movimiento m = Movimiento.builder()
      .movimientoId(1L)
      .fecha(LocalDateTime.now())
      .cuenta(Cuenta.builder()
          .numeroCuenta("123")
          .cliente(Cliente.builder().nombre("Juan").build())
          .tipoCuenta(TipoCuentaEnum.AHORRO)
          .estado(TipoEstadoEnum.True)
        .build()
      )
      .valor(new BigDecimal("10.00"))
      .saldo(new BigDecimal("110.00")).build();
    when(movimientosRepository.findAllByClienteAndFecha(anyLong(), any(), any(), any())).thenReturn(List.of(m));

    List<MovimientoRpt> resultado = movimientosService.reporte(1L, LocalDate.of(2026,1,1), LocalDate.of(2026,1,2));

    assertNotNull(resultado);
    assertEquals(1, resultado.size());
  }

  @Test
  void reporte_fechaMayorHoy_debeLanzarSimpleException() {
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    assertThrows(SimpleException.class, () -> movimientosService.reporte(1L, tomorrow, null));
  }

}

