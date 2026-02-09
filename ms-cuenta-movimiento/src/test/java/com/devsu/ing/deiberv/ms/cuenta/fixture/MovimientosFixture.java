package com.devsu.ing.deiberv.ms.cuenta.fixture;

import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoRpt;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoCuentaEnum;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class MovimientosFixture {
  public static List<Movimiento> obtenerMovimientos() {
    return List.of(
      Movimiento.builder()
        .movimientoId(1L)
        .fecha(LocalDateTime.now(ZoneId.systemDefault()))
        .cuenta(Cuenta.builder()
          .numeroCuenta("225487")
          .tipoCuenta(TipoCuentaEnum.CORRIENTE)
          .estado(TipoEstadoEnum.True)
          .cliente(Cliente.builder()
            .clienteId(2L)
            .nombre("Marianela Montalvo")
            .build())
          .build())
        .saldoInicial(BigDecimal.valueOf(100))
        .valor(BigDecimal.valueOf(600))
        .saldo(BigDecimal.valueOf(700))
        .build()
    );
  }

  public static List<MovimientoRpt> obtenerMovimientosVm() {
    return List.of(
      MovimientoRpt.builder()
        .fecha(LocalDate.now())
        .cliente("Marianela Montalvo")
        .numeroCuenta("225487")
        .tipoCuenta("Corriente")
        .saldoInicial(BigDecimal.valueOf(100))
        .estado(true)
        .movimiento(BigDecimal.valueOf(600))
        .saldoDisponible(BigDecimal.valueOf(700))
        .build(),
      MovimientoRpt.builder()
        .fecha(LocalDate.now())
        .cliente("Marianela Montalvo")
        .numeroCuenta("496825")
        .tipoCuenta("Ahorro")
        .saldoInicial(BigDecimal.valueOf(540))
        .estado(true)
        .movimiento(BigDecimal.valueOf(-540))
        .saldoDisponible(BigDecimal.ZERO)
        .build()
    );
  }
}
