package com.devsu.ing.deiberv.ms.cuenta.service.interfaces;

import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoRequest;
import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoRpt;
import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoSaldoDto;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface MovimientosService {

  /**
   * Realiza el registro de un movimiento sobre la cuenta
   * @param movimientoRequest {@link MovimientoRequest}
   * @return {@link MovimientoSaldoDto}
   */
  MovimientoSaldoDto registrar(MovimientoRequest movimientoRequest);

  /**
   * Genera el reporte de movimientos para un cliente
   * @param clienteId {@link Long}
   * @param fechaInicial {@link LocalDate}
   * @param fechaFin {@link LocalDate}
   * @return Lista de movimientos {@link List<MovimientoRpt>}
   */
  List<MovimientoRpt> reporte(Long clienteId, LocalDate fechaInicial, @Nullable LocalDate fechaFin);

}
