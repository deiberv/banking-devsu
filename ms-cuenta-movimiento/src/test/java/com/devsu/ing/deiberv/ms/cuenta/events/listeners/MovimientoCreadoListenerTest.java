package com.devsu.ing.deiberv.ms.cuenta.events.listeners;

import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuenta.events.MovimientoCreadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.repository.CuentasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoCreadoListenerTest {

  @Mock
  private CuentasRepository cuentasRepository;

  @InjectMocks
  private MovimientoCreadoListener listener;

  @Captor
  private ArgumentCaptor<Cuenta> cuentaCaptor;

  @BeforeEach
  void setUp() {
  }

  @Test
  void onApplicationEvent_cuandoCuentaExiste_debeActualizarSaldoYGuardar() {
    // preparar datos
    Cuenta cuenta = Cuenta.builder()
      .cuentaId(1L)
      .numeroCuenta("12345")
      .saldo(new BigDecimal("100.00"))
      .build();

    Movimiento movimiento = Movimiento.builder()
      .movimientoId(1L)
      .valor(new BigDecimal("50.25"))
      .fecha(LocalDateTime.now())
      .cuenta(cuenta)
      .build();

    MovimientoCreadoEvent event = new MovimientoCreadoEvent(this, movimiento);

    when(cuentasRepository.findByNumeroCuenta("12345")).thenReturn(Optional.of(cuenta));
    when(cuentasRepository.save(any(Cuenta.class))).thenAnswer(inv -> inv.getArgument(0));

    // ejecutar
    listener.onApplicationEvent(event);

    // verificar
    verify(cuentasRepository, times(1)).findByNumeroCuenta("12345");
    verify(cuentasRepository, times(1)).save(cuentaCaptor.capture());
    Cuenta saved = cuentaCaptor.getValue();
    assertEquals(new BigDecimal("150.25"), saved.getSaldo());
  }

  @Test
  void onApplicationEvent_cuandoCuentaExiste_debeActualizarSaldoRestaYGuardar() {
    // preparar datos
    Cuenta cuenta = Cuenta.builder()
      .cuentaId(1L)
      .numeroCuenta("12345")
      .saldo(new BigDecimal("100.00"))
      .build();

    Movimiento movimiento = Movimiento.builder()
      .movimientoId(1L)
      .valor(new BigDecimal("-50"))
      .fecha(LocalDateTime.now())
      .cuenta(cuenta)
      .build();

    MovimientoCreadoEvent event = new MovimientoCreadoEvent(this, movimiento);

    when(cuentasRepository.findByNumeroCuenta("12345")).thenReturn(Optional.of(cuenta));
    when(cuentasRepository.save(any(Cuenta.class))).thenAnswer(inv -> inv.getArgument(0));

    // ejecutar
    listener.onApplicationEvent(event);

    // verificar
    verify(cuentasRepository, times(1)).findByNumeroCuenta("12345");
    verify(cuentasRepository, times(1)).save(cuentaCaptor.capture());
    Cuenta saved = cuentaCaptor.getValue();
    assertEquals(new BigDecimal("50.00"), saved.getSaldo());
  }

  @Test
  void onApplicationEvent_cuandoCuentaNoExiste_debeLanzarException() {
    Cuenta cuenta = Cuenta.builder().cuentaId(2L).numeroCuenta("99999").saldo(new BigDecimal("0.00")).build();
    Movimiento movimiento = Movimiento.builder().movimientoId(2L).valor(new BigDecimal("10.00")).fecha(LocalDateTime.now()).cuenta(cuenta).build();
    MovimientoCreadoEvent event = new MovimientoCreadoEvent(this, movimiento);

    when(cuentasRepository.findByNumeroCuenta("99999")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> listener.onApplicationEvent(event));

    verify(cuentasRepository, times(1)).findByNumeroCuenta("99999");
    verify(cuentasRepository, never()).save(any());
  }

  @Test
  void onApplicationEvent_saveLanzaException_debePropagar() {
    Cuenta cuenta = Cuenta.builder().cuentaId(3L).numeroCuenta("55555").saldo(new BigDecimal("20.00")).build();
    Movimiento movimiento = Movimiento.builder().movimientoId(3L).valor(new BigDecimal("5.00")).fecha(LocalDateTime.now()).cuenta(cuenta).build();
    MovimientoCreadoEvent event = new MovimientoCreadoEvent(this, movimiento);

    when(cuentasRepository.findByNumeroCuenta("55555")).thenReturn(Optional.of(cuenta));
    when(cuentasRepository.save(any(Cuenta.class))).thenThrow(new RuntimeException("DB save failed"));

    RuntimeException ex = assertThrows(RuntimeException.class, () -> listener.onApplicationEvent(event));
    assertTrue(ex.getMessage().contains("DB save failed"));

    verify(cuentasRepository, times(1)).findByNumeroCuenta("55555");
    verify(cuentasRepository, times(1)).save(any(Cuenta.class));
  }

}
